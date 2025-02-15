#!/bin/bash
source ./scripts/init.sh

fd -t f . $spring_path/service/base/ -x rm {}
fd -t f . $spring_path/pojo/vo/base -x rm {}
fd -t f . $spring_path/pojo/dto/base -x rm {}
fd -t f . $spring_path/pojo/entity -E "Base*.java" -x rm {}