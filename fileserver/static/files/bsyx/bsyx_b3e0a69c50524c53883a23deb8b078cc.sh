#!/bin/bash
# ���ݿ���֤
 user=mysql
 password=123456
 host=172.16.110.197
 db_name=srrp
# ����
 backup_path="/home/backup"
 date=$(date +"%Y%m%d_%H%M%S")
# ���õ����ļ���ȱʡȨ��
 umask 177
# Dump���ݿ⵽SQL�ļ�
mysqldump -h$host -u$user -P3306 -p$password  $db_name > $backup_path/$db_name-$date.sql
