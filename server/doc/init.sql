-- gandalf.t_car_driver_info definition

CREATE TABLE `t_car_driver_info` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `name` varchar(255) DEFAULT NULL,
    `status` int DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_soa_receive_info definition

CREATE TABLE `t_soa_receive_info` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `client_code` varchar(255) DEFAULT NULL,
    `client_id` bigint DEFAULT NULL,
    `data` varchar(4096) DEFAULT NULL,
    `decrypt_data` varchar(4096) DEFAULT NULL,
    `ip` varchar(255) DEFAULT NULL,
    `msg` varchar(255) DEFAULT NULL,
    `msg_code` varchar(255) DEFAULT NULL,
    `receive_time` datetime DEFAULT NULL,
    `send_time` datetime DEFAULT NULL,
    `status` int DEFAULT NULL,
    `version` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_soa_send_info definition

CREATE TABLE `t_soa_send_info` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `back_date` varchar(4096) DEFAULT NULL,
    `buss_id` bigint DEFAULT NULL,
    `send_data` varchar(4096) DEFAULT NULL,
    `send_time` datetime DEFAULT NULL,
    `third_code` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_sys_calendar_info definition

CREATE TABLE `t_sys_calendar_info` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `date_name` varchar(255) DEFAULT NULL,
    `status` int DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_sys_dictionary_info definition

CREATE TABLE `t_sys_dictionary_info` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `status` int DEFAULT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_sys_pic definition

CREATE TABLE `t_sys_pic` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `data` longblob,
                             `object_id` bigint DEFAULT NULL,
                             `type` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_sys_role definition

CREATE TABLE `t_sys_role` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_soa_via_info definition

CREATE TABLE `t_soa_via_info` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `card_code` varchar(255) DEFAULT NULL,
    `card_type` int DEFAULT NULL,
    `gate_id` varchar(255) DEFAULT NULL,
    `gate_info` varchar(255) DEFAULT NULL,
    `status` int DEFAULT NULL,
    `student_id` bigint DEFAULT NULL,
    `via_result` int DEFAULT NULL,
    `via_time` datetime DEFAULT NULL,
    `via_type` int DEFAULT NULL,
    `receive_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK6wndt4gmv69rpnc0ke1flq2c5` (`receive_id`),
    CONSTRAINT `FK6wndt4gmv69rpnc0ke1flq2c5` FOREIGN KEY (`receive_id`) REFERENCES `t_soa_receive_info` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_sys_department_info definition

CREATE TABLE `t_sys_department_info` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `status` int DEFAULT NULL,
    `third_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `parent_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKn4t772vqy1pl5rn8te1yo5qcs` (`parent_id`),
    CONSTRAINT `FKn4t772vqy1pl5rn8te1yo5qcs` FOREIGN KEY (`parent_id`) REFERENCES `t_sys_department_info` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_sys_function definition

CREATE TABLE `t_sys_function` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `parent_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK61e3n999xhnepnu6kkrxwugx5` (`parent_id`),
    CONSTRAINT `FK61e3n999xhnepnu6kkrxwugx5` FOREIGN KEY (`parent_id`) REFERENCES `t_sys_function` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_sys_role_function definition

CREATE TABLE `t_sys_role_function` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `function_id` bigint DEFAULT NULL,
                                       `role_id` bigint DEFAULT NULL,
                                       PRIMARY KEY (`id`),
    KEY `FKsacxqpmsxpdvim3s98p8dfq2j` (`function_id`),
    KEY `FKblysof3vnbq01nwocdnu6qktm` (`role_id`),
    CONSTRAINT `FKblysof3vnbq01nwocdnu6qktm` FOREIGN KEY (`role_id`) REFERENCES `t_sys_role` (`id`),
    CONSTRAINT `FKsacxqpmsxpdvim3s98p8dfq2j` FOREIGN KEY (`function_id`) REFERENCES `t_sys_function` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_sys_user definition

CREATE TABLE `t_sys_user` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `is_new` int DEFAULT NULL,
    `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `status` int DEFAULT NULL,
    `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `department_id` bigint DEFAULT NULL,
    `gender_id` bigint DEFAULT NULL,
    `position_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKkep8tjyumbipou48x4qglb1ja` (`department_id`),
    KEY `FKeiri1ofc1xinpnhu6khlx8bea` (`gender_id`),
    KEY `FKg8fa1tsggmjj6emxn6xs8m2ye` (`position_id`),
    CONSTRAINT `FKeiri1ofc1xinpnhu6khlx8bea` FOREIGN KEY (`gender_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKg8fa1tsggmjj6emxn6xs8m2ye` FOREIGN KEY (`position_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKkep8tjyumbipou48x4qglb1ja` FOREIGN KEY (`department_id`) REFERENCES `t_sys_department_info` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_sys_user_role definition

CREATE TABLE `t_sys_user_role` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `role_id` bigint DEFAULT NULL,
                                   `user_id` bigint DEFAULT NULL,
                                   PRIMARY KEY (`id`),
    KEY `FKl2o4hxlyp8d0nt2guqsu1qssr` (`role_id`),
    KEY `FKpshjnr5jb9asjww3lc7vk46dq` (`user_id`),
    CONSTRAINT `FKl2o4hxlyp8d0nt2guqsu1qssr` FOREIGN KEY (`role_id`) REFERENCES `t_sys_role` (`id`),
    CONSTRAINT `FKpshjnr5jb9asjww3lc7vk46dq` FOREIGN KEY (`user_id`) REFERENCES `t_sys_user` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_car_applicant_info definition

CREATE TABLE `t_car_applicant_info` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `applicant_code` varchar(255) DEFAULT NULL,
    `car_count` int DEFAULT NULL,
    `end_time` datetime DEFAULT NULL,
    `is_area` int DEFAULT NULL,
    `lead_mobile` varchar(255) DEFAULT NULL,
    `lead_name` varchar(255) DEFAULT NULL,
    `lead_position` varchar(255) DEFAULT NULL,
    `reason` varchar(255) DEFAULT NULL,
    `refuse_reason` varchar(255) DEFAULT NULL,
    `route_end` varchar(255) DEFAULT NULL,
    `route_middle` varchar(255) DEFAULT NULL,
    `route_start` varchar(255) DEFAULT NULL,
    `start_time` datetime DEFAULT NULL,
    `status` int DEFAULT NULL,
    `applicant_user_id` bigint DEFAULT NULL,
    `car_type_id` bigint DEFAULT NULL,
    `department_id` bigint DEFAULT NULL,
    `use_type_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKcsm15x7y6k9imyqvo9yybffw6` (`applicant_user_id`),
    KEY `FKc80bevy1y9pgelg2mq7r08ehh` (`car_type_id`),
    KEY `FKdw3lgl0wsudk1yobdc5gpulgv` (`department_id`),
    KEY `FKgewtocvbwe8c40ur4m3m3jys3` (`use_type_id`),
    CONSTRAINT `FKc80bevy1y9pgelg2mq7r08ehh` FOREIGN KEY (`car_type_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKcsm15x7y6k9imyqvo9yybffw6` FOREIGN KEY (`applicant_user_id`) REFERENCES `t_sys_user` (`id`),
    CONSTRAINT `FKdw3lgl0wsudk1yobdc5gpulgv` FOREIGN KEY (`department_id`) REFERENCES `t_sys_department_info` (`id`),
    CONSTRAINT `FKgewtocvbwe8c40ur4m3m3jys3` FOREIGN KEY (`use_type_id`) REFERENCES `t_sys_dictionary_info` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_car_info definition

CREATE TABLE `t_car_info` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `buy_time` datetime DEFAULT NULL,
                              `carno` varchar(255) DEFAULT NULL,
    `check_time` datetime DEFAULT NULL,
    `insurance_time` datetime DEFAULT NULL,
    `maintenance_time` datetime DEFAULT NULL,
    `mileage` double DEFAULT NULL,
    `car_type_id` bigint DEFAULT NULL,
    `department_id` bigint DEFAULT NULL,
    `status` bigint DEFAULT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKdlga75f11hspv3ex1lc27qcsl` (`car_type_id`),
    KEY `FKl7dx8bluccedkhg4vudxtxiys` (`department_id`),
    KEY `FKmbiikdo2uu0tn1fgi91wf709p` (`status`),
    KEY `FKe0c9xv6xlgrulobhf5cux5wci` (`user_id`),
    CONSTRAINT `FKdlga75f11hspv3ex1lc27qcsl` FOREIGN KEY (`car_type_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKe0c9xv6xlgrulobhf5cux5wci` FOREIGN KEY (`user_id`) REFERENCES `t_sys_user` (`id`),
    CONSTRAINT `FKl7dx8bluccedkhg4vudxtxiys` FOREIGN KEY (`department_id`) REFERENCES `t_sys_department_info` (`id`),
    CONSTRAINT `FKmbiikdo2uu0tn1fgi91wf709p` FOREIGN KEY (`status`) REFERENCES `t_sys_dictionary_info` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_car_operation_info definition

CREATE TABLE `t_car_operation_info` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `driver_name` varchar(255) DEFAULT NULL,
    `end_time` datetime DEFAULT NULL,
    `start_time` datetime DEFAULT NULL,
    `status` int DEFAULT NULL,
    `car_id` bigint DEFAULT NULL,
    `type_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKhbyqoxpgiyrbvcm8ppcj8f40` (`car_id`),
    KEY `FK2dk9wpuif9iddqsoolu5rh8se` (`type_id`),
    CONSTRAINT `FK2dk9wpuif9iddqsoolu5rh8se` FOREIGN KEY (`type_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKhbyqoxpgiyrbvcm8ppcj8f40` FOREIGN KEY (`car_id`) REFERENCES `t_car_info` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_st_applicant_info definition

CREATE TABLE `t_st_applicant_info` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `address` varchar(255) DEFAULT NULL,
    `applicant_user_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
    `end_time` datetime DEFAULT NULL,
    `reason` varchar(255) DEFAULT NULL,
    `start_time` datetime DEFAULT NULL,
    `create_user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKg0xfb1lcs2x09m09gtv8xrr0c` (`create_user_id`),
    CONSTRAINT `FKg0xfb1lcs2x09m09gtv8xrr0c` FOREIGN KEY (`create_user_id`) REFERENCES `t_sys_user` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_st_leave_limit definition

CREATE TABLE `t_st_leave_limit` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `limit_value` int DEFAULT NULL,
                                    `department_id` bigint DEFAULT NULL,
                                    PRIMARY KEY (`id`),
    KEY `FKh10itw3684n9ahtkts6grm9mg` (`department_id`),
    CONSTRAINT `FKh10itw3684n9ahtkts6grm9mg` FOREIGN KEY (`department_id`) REFERENCES `t_sys_department_info` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_st_student_info definition

CREATE TABLE `t_st_student_info` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `card_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `end_time` datetime DEFAULT NULL,
    `identity_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `in_status` int DEFAULT NULL,
    `last_via_time` datetime DEFAULT NULL,
    `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `native_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `start_time` datetime DEFAULT NULL,
    `status` int DEFAULT NULL,
    `third_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `department_id` bigint DEFAULT NULL,
    `gender_id` bigint DEFAULT NULL,
    `type_id` bigint DEFAULT '20',
    `face_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `hik_vision_status` int DEFAULT NULL,
    `jie_shun_status` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKjkw31dg326sx88ngacmjcsdsi` (`department_id`),
    KEY `FK7kr2efkq72gdlw4o825kf120j` (`gender_id`),
    KEY `FKd4m807r1m3aiexnu8rnmfwt8v` (`type_id`),
    CONSTRAINT `FK7kr2efkq72gdlw4o825kf120j` FOREIGN KEY (`gender_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKd4m807r1m3aiexnu8rnmfwt8v` FOREIGN KEY (`type_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKjkw31dg326sx88ngacmjcsdsi` FOREIGN KEY (`department_id`) REFERENCES `t_sys_department_info` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4733 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- gandalf.t_car_applicant_approval definition

CREATE TABLE `t_car_applicant_approval` (
                                            `id` bigint NOT NULL AUTO_INCREMENT,
                                            `approval_time` datetime DEFAULT NULL,
                                            `code` varchar(255) DEFAULT NULL,
    `opinion` varchar(255) DEFAULT NULL,
    `status` int DEFAULT NULL,
    `approval_user_id` bigint DEFAULT NULL,
    `applicant_id` bigint DEFAULT NULL,
    `department_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKt7rq65k94ds5r3cqjd3jeolu6` (`approval_user_id`),
    KEY `FKesld17ifmqiuv2p9sxfqy4ivq` (`applicant_id`),
    KEY `FKb642oi4efppkvn4cm6srstwn5` (`department_id`),
    CONSTRAINT `FKb642oi4efppkvn4cm6srstwn5` FOREIGN KEY (`department_id`) REFERENCES `t_sys_department_info` (`id`),
    CONSTRAINT `FKesld17ifmqiuv2p9sxfqy4ivq` FOREIGN KEY (`applicant_id`) REFERENCES `t_car_applicant_info` (`id`),
    CONSTRAINT `FKt7rq65k94ds5r3cqjd3jeolu6` FOREIGN KEY (`approval_user_id`) REFERENCES `t_sys_user` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_car_control_info definition

CREATE TABLE `t_car_control_info` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `address` varchar(255) DEFAULT NULL,
    `alert_time` int DEFAULT NULL,
    `car_no` varchar(255) DEFAULT NULL,
    `control_code` varchar(255) DEFAULT NULL,
    `driver_name` varchar(255) DEFAULT NULL,
    `end_time` datetime DEFAULT NULL,
    `estimate_end_time` datetime DEFAULT NULL,
    `estimate_start_time` datetime DEFAULT NULL,
    `lead_mobile` varchar(255) DEFAULT NULL,
    `lead_name` varchar(255) DEFAULT NULL,
    `lead_position` varchar(255) DEFAULT NULL,
    `memo` varchar(255) DEFAULT NULL,
    `mileage` double DEFAULT NULL,
    `route_end` varchar(255) DEFAULT NULL,
    `route_middle` varchar(255) DEFAULT NULL,
    `route_start` varchar(255) DEFAULT NULL,
    `start_time` datetime DEFAULT NULL,
    `status` int DEFAULT NULL,
    `thirdid` varchar(255) DEFAULT NULL,
    `use_name` varchar(255) DEFAULT NULL,
    `applicant_user_id` bigint DEFAULT NULL,
    `car_id` bigint DEFAULT NULL,
    `applicant_id` bigint DEFAULT NULL,
    `control_user_id` bigint DEFAULT NULL,
    `department_id` bigint DEFAULT NULL,
    `use_type_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK6oy12facbcxm19rt68sup33te` (`applicant_user_id`),
    KEY `FK6sf8uqqwsx6jwp81a8yawj0ow` (`car_id`),
    KEY `FK3wcc563wpgyertsgrystymvet` (`applicant_id`),
    KEY `FKbq4hg96fh1ff3c1afq1wym9ne` (`control_user_id`),
    KEY `FKm2fhwhavsn3tnermiwlie4qjg` (`department_id`),
    KEY `FKhrapr48e1250o2no4i9g8o6hk` (`use_type_id`),
    CONSTRAINT `FK3wcc563wpgyertsgrystymvet` FOREIGN KEY (`applicant_id`) REFERENCES `t_car_applicant_info` (`id`),
    CONSTRAINT `FK6oy12facbcxm19rt68sup33te` FOREIGN KEY (`applicant_user_id`) REFERENCES `t_sys_user` (`id`),
    CONSTRAINT `FK6sf8uqqwsx6jwp81a8yawj0ow` FOREIGN KEY (`car_id`) REFERENCES `t_car_info` (`id`),
    CONSTRAINT `FKbq4hg96fh1ff3c1afq1wym9ne` FOREIGN KEY (`control_user_id`) REFERENCES `t_sys_user` (`id`),
    CONSTRAINT `FKhrapr48e1250o2no4i9g8o6hk` FOREIGN KEY (`use_type_id`) REFERENCES `t_sys_dictionary_info` (`id`),
    CONSTRAINT `FKm2fhwhavsn3tnermiwlie4qjg` FOREIGN KEY (`department_id`) REFERENCES `t_sys_department_info` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gandalf.t_st_leave_info definition

CREATE TABLE `t_st_leave_info` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `end_date` datetime DEFAULT NULL,
                                   `estimate_end_time` datetime DEFAULT NULL,
                                   `estimate_start_time` datetime DEFAULT NULL,
                                   `start_date` datetime DEFAULT NULL,
                                   `status` int DEFAULT NULL,
                                   `applicant_id` bigint DEFAULT NULL,
                                   `student_id` bigint DEFAULT NULL,
                                   `data_status` int DEFAULT NULL,
                                   PRIMARY KEY (`id`),
    KEY `FKjcixwpipkbkn2bjnqcdjt9lq8` (`applicant_id`),
    KEY `FK2hl1jr1jvfebohn8qguyrdy92` (`student_id`),
    CONSTRAINT `FK2hl1jr1jvfebohn8qguyrdy92` FOREIGN KEY (`student_id`) REFERENCES `t_st_student_info` (`id`),
    CONSTRAINT `FKjcixwpipkbkn2bjnqcdjt9lq8` FOREIGN KEY (`applicant_id`) REFERENCES `t_st_applicant_info` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;