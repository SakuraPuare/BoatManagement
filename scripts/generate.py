import pathlib
import re
import logging
from dataclasses import dataclass

PROJECT_NAME = "boatmanagement"
DOMAIN = "com.sakurapuare"
BASE_PATH = pathlib.Path(__file__).parent.parent
SPRING_PATH = BASE_PATH / "src/main/java/" / DOMAIN.replace(".", "/") / PROJECT_NAME


ENTITY_PATH = SPRING_PATH / "pojo/entity"
DTO_PATH = SPRING_PATH / "pojo/dto/base"
VO_PATH = SPRING_PATH / "pojo/vo/base"
SERVICE_PATH = SPRING_PATH / "service"

# Set up logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

@dataclass
class Entity:
    entity_name: str
    import_list: list[str]
    table_name: str
    extend_class: str
    define_list: list[tuple[str, str]]


def parse_entity_file(file_path: pathlib.Path) -> Entity:
    logger.info(f"Parsing entity file: {file_path}")
    try:
        file_data = file_path.read_text()
        
        import_part, define_part = file_data.split("@Data")
        
        import_list = re.findall(r"import\s+(.+);", import_part)
        table_name = re.findall(r"""@ApiModel\("(.+)"\)""", define_part)[0]
        entity_name = re.findall(r"public class\s+(\w+)", define_part)[0]
        extend_class = re.findall(r"extends\s+(\w+)", define_part)[0]
        define_list = re.findall(r"""@ApiModelProperty\("(.+)?"\)\n\s*(.+);""", define_part)

        entity = Entity(entity_name, import_list, table_name, extend_class, define_list)
        logger.debug(f"Successfully parsed entity: {entity_name}")
        return entity
    except Exception as e:
        logger.error(f"Error parsing entity file {file_path}: {str(e)}")
        raise

def filter_import_list(import_list: list[str], additional_import: list[str] = None) -> list[str]:
    
    if additional_import is None:
        additional_import = []

    return [
        f'import {import_item};'
        for import_item in import_list
        if not import_item.startswith(DOMAIN)
        and not import_item.startswith("com.mybatisflex")
        and not import_item.startswith("lombok")
        and not import_item.startswith("io.swagger")
        and not import_item.startswith("java.io.")
    ] + additional_import

def generate_import_list(filtered_import_list: list[str]) -> str:

    def fmt_import_list(import_list: list[str]) -> str:
        return '\n'.join(
            sorted(
                import_list
            )
        )

    java_import_list = [
        i for i in filtered_import_list
        if 'java.' in i
    ]
    other_import_list = [
        i for i in filtered_import_list
        if 'java.' not in i
    ]
    return f"""
{fmt_import_list(other_import_list)}
{('\n' + fmt_import_list(java_import_list)) if java_import_list else ''}"""


def generate_dto_file(entity: Entity):
    if 'ndto' in entity.table_name:
        logger.debug(f"Skipping DTO generation for {entity.entity_name} (ndto flag)")
        return

    file_path = DTO_PATH / f"Base{entity.entity_name}DTO.java"
    logger.info(f"Generating DTO file: {file_path}")

    filtered_import_list = filter_import_list(entity.import_list, [
        f'import {DOMAIN}.{PROJECT_NAME}.pojo.dto.{entity.extend_class}DTO;',
        f'import io.swagger.annotations.ApiModelProperty;',
        f'import lombok.Data;',
        f'import lombok.EqualsAndHashCode;',
    ])

    for i in entity.define_list:
        if 'LocalDateTime' in i[1]:
            filtered_import_list.append(f"import com.fasterxml.jackson.annotation.JsonFormat;")
            break

    template = f"""package {DOMAIN}.{PROJECT_NAME}.pojo.dto.base;
{generate_import_list(filtered_import_list)}
@Data
@EqualsAndHashCode(callSuper = true)
public class Base{entity.entity_name}DTO extends {entity.extend_class}DTO {{

{
    '\n'.join(
        [
            f"{
                f'    @ApiModelProperty(\"{define[0]}\")\n{
                    f'    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")\n' 
                    if 'LocalDateTime' in define[1] 
                    else ''
                }'
                if define[0]
                else ''
            }    {define[1]};\n"
            for define in entity.define_list 
            if 'serverside' not in define[0]
            and 'Long id' not in define[1]
        ]
    )
}
}}
"""
    
    file_path.write_text(template)
    logger.debug(f"Successfully generated DTO file for {entity.entity_name}")
    pass

def generate_vo_file(entity: Entity):
    if 'nvo' in entity.table_name:
        logger.debug(f"Skipping VO generation for {entity.entity_name} (nvo flag)")
        return

    file_path = VO_PATH / f"Base{entity.entity_name}VO.java"
    logger.info(f"Generating VO file: {file_path}")

    filtered_import_list = filter_import_list(entity.import_list, [
        f'import {DOMAIN}.{PROJECT_NAME}.pojo.vo.{entity.extend_class}VO;',
        f'import io.swagger.annotations.ApiModelProperty;',
        f'import lombok.Data;',
        f'import lombok.EqualsAndHashCode;',
    ])

    template = f"""package {DOMAIN}.{PROJECT_NAME}.pojo.vo.base;
{generate_import_list(filtered_import_list)}
@Data
@EqualsAndHashCode(callSuper = true)
public class Base{entity.entity_name}VO extends {entity.extend_class}VO {{

{
    '\n'.join(
        [
            f"{
                f'    @ApiModelProperty(\"{define[0]}\")\n'
                if define[0]
                else ''
            }    {define[1]};\n"
            for define in entity.define_list 
            if 'clientside' not in define[0]
        ]
    )
}
}}
"""
    
    file_path.write_text(template)
    logger.debug(f"Successfully generated VO file for {entity.entity_name}")
    pass

def generate_service_file(entity: Entity):
    file_path = SERVICE_PATH / f"{entity.entity_name}Service.java"
    
    if file_path.exists():
        logger.debug(f"Skipping Service generation for {entity.entity_name} (file exists)")
        return

    logger.info(f"Generating Service file: {file_path}")

    import_list = filter_import_list([], [
        f'import {DOMAIN}.{PROJECT_NAME}.service.base.Base{entity.entity_name}Service;',
        f'import org.springframework.stereotype.Service;',
        f'import lombok.RequiredArgsConstructor;',
    ])

    template = f"""package {DOMAIN}.{PROJECT_NAME}.service;
{generate_import_list(import_list)}
@Service
@RequiredArgsConstructor
public class {entity.entity_name}Service extends Base{entity.entity_name}Service {{
}}
"""

    file_path.write_text(template)
    logger.debug(f"Successfully generated Service file for {entity.entity_name}")
    pass

def main():
    logger.info("Starting code generation process")
    
    # Delete previous generated files
    logger.info("Cleaning up previous generated files")
    for file in DTO_PATH.glob("*.java"):
        logger.debug(f"Deleting {file}")
        file.unlink()
    for file in VO_PATH.glob("*.java"):
        logger.debug(f"Deleting {file}")
        file.unlink()

    # Generate new files
    logger.info("Starting file generation")
    for file in ENTITY_PATH.glob("*.java"):
        if not file.name.startswith("Base"):
            try:
                entity = parse_entity_file(file)
                generate_dto_file(entity)
                generate_vo_file(entity)
                generate_service_file(entity)
            except Exception as e:
                logger.error(f"Error processing {file}: {str(e)}")
                continue
    
    logger.info("Code generation completed")

if __name__ == "__main__":
    main()