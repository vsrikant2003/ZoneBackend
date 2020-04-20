insert into category values(1,'CMB/LAP','2018-09-05 10:25:21','Laptops',NULL);

insert into category values(2,'CMB/OEQ','2018-06-06 11:24:37','Office Equipments',NULL);

insert into category values(3,'CMB/DEV','2018-06-06 11:24:37','Development Devices',NULL);

insert into role values(1,1,'Admin',1);

insert into role values(2,1,'user',1);

insert into role values(3,0,'developer',1);

insert into role values(4,0,'Tester',1);

insert into user values(1,1,'2018-03-09 14:13:31','bathiyat@zone24x7.com','Bathiya','Tennakoon','BathiyaT',1);

insert into user values(2,1,'2018-03-09 14:13:31','amalik@zone24x7.com','Amali','Kumuduni','AmaliK',2);

insert into user values(3,1,'2018-03-09 14:13:31','Test@zone24x7.com','Test','Test','Test',2);

insert into allocation_type values(1,'Employee');
insert into allocation_type values(2,'Project');
insert into allocation_type values(3,'Other');

insert into status values(0,'Avaliable');

insert into status values(1,'Allocated');

insert into status values(2,'Deallocated');

insert into operation values(1,'View');

insert into operation values(2,'Add');

insert into operation values(3,'Edit');

insert into operation values(4,'Allocate');

insert into operation values(5,'Dispose');

insert into operation values(6,'Delete');

insert into permission values(1,1,1,1);

insert into permission values(2,1,2,1);

insert into permission values(3,1,3,1);

insert into permission values(4,1,4,1);

insert into permission values(5,1,5,1);

insert into permission values(6,1,6,1);

insert into permission values(8,2,1,2);

insert into permission values(9,2,2,2);

insert into permission values(10,2,3,2);

insert into permission values(11,2,4,2);

insert into permission values(12,2,5,2);

insert into attr_data_type values(1,'String');

insert into attr_data_type values(2,'Integer');

insert into attr_data_type values(3,'Float');

insert into attr_data_type values(4,'Currency');

insert into attr_data_type values(5,'Date');

insert into attribute values(1,'2018-05-09 15:50:40','Office EQ Make',1,1);

insert into attribute values(2,'2018-08-31 16:15:49','Models',1,1);

insert into attribute values(3,'2018-05-09 15:50:40','Serial Number',1,1);

insert into attribute values(4,'2018-08-31 16:02:35','Description',1,1);

insert into attribute values(5,'2018-05-09 15:50:40','Purchased Date',5,1);

insert into attribute values(6,'2018-05-09 15:50:40','Location',1,1);

insert into category_attribute values(1,0,1,1);

insert into category_attribute values(2,0,2,1);

insert into category_attribute values(3,0,3,1);

insert into category_attribute values(4,0,4,1);

insert into category_attribute values(5,0,5,1);

insert into attribute_value values(1,'Panasonic',1);

insert into attribute_value values(2,'HP',1);

insert into attribute_value values(3,'Nakamichi',1);

insert into attribute_value values(4,'Logitech',1);

insert into attribute_value values(5,'Zenith',1);

insert into attribute_value values(6,'Brother',1);

insert into resource values(1,'CMB/LAP/0001','2018-05-18 17:27:20',1,0,1);

insert into resource values(2,'CMB/LAP/0002','2018-05-09 15:50:40',1,0,1);

insert into resource values(3,'CMB/LAP/0003','2018-05-09 15:50:40',2,0,2);

insert into resource_attribute values(1,'Samsung',2,1);

insert into resource values(6,'CMB/LAP/0006','2018-05-18 17:27:20',1,0,1);

insert into resource_attribute values(10,'Samsung',2,1);

insert into resource values(7,'CMB/LAP/0007','2018-05-18 17:27:20',1,1,1);

insert into resource_attribute values(11,'Dell',2,1);

insert into resourcebin values(1,'CMB/LAP/0005','2018-05-18 17:27:20','not required',1,0,1);

insert into resourcebin values(2,'CMB/OEQ/0003','2018-05-09 15:50:40','Not required',2,0,2);

insert into resourcebin_attribute values(1,'laptop',2,1);

insert into allocation values(1,'2018-09-24 17:27:20','2018-09-24',NULL,3,7,1,1);

insert into other_allocation values(2,'BathiyaT',1);










