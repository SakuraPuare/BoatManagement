
./mvnw clean install package -DskipTests

# 导入sql
mariadb -u root -p -h 127.0.0.1 -P 3306 -D boatmanagement < scripts/database.sql

# GenSQL

/usr/lib/jvm/openjdk-zulu21-ca-fx-bin/bin/java  -jar target/SQLGen-jar-with-dependencies.jar

# 执行sh文件
sh scripts/generate_dto_vo.sh

# 执行sh文件
sh scripts/generate_service.sh
