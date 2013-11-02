create database learning;

create table `parent_nodes`(
	`id` int not null auto_increment,
	`name` varchar(255) not null,
	`parentId` int,

	primary key(id)
);

create table `service`(
	`id` int not null  auto_increment,
	`name` varchar(255) not null,
	`globalSmAlertCond` int not null,
	`individualAlertLevel` varchar(20) not null,
	`description` varchar(1024),
	`overallAlertLevel` varchar(20) not null,
	`pollingInterval` int not null,
	`transition` int not null,
	`url` varchar(4096) not null,
	`parentId` int default 1,

	primary key(id),
	check(name<>''),
	check(pollingInterval>0),
	check(transition>=0),
	FOREIGN KEY (parentId) REFERENCES parent_nodes(id)
);

// prepare init data for table parent_nodes
insert into parent_nodes(name) values('__root__');
insert into parent_nodes(name, parentId) values('ttt', 1);
insert into parent_nodes(name, parentId) values('ttt2', 1);
