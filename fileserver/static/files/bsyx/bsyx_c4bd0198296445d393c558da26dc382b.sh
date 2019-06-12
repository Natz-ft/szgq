#!/bin/bash
# 数据库认证
 user=mysql
 password=123456
 host=172.16.110.197
 db_name=srrp
# 其它
 backup_path="/home/backup"
 date=$(date +"%Y%m%d_%H%M%S")
# 设置导出文件的缺省权限
 umask 177
# Dump数据库到SQL文件
mysqldump -h$host -u$user -P3306 -p$password  $db_name > $backup_path/$db_name-$date.sql
