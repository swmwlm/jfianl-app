DROP TABLE IF EXISTS project;
CREATE TABLE project (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL COMMENT '项目名称',
  remark TEXT COMMENT '备注',
  cost INT NOT NULL COMMENT '成本',
  payback_at TIMESTAMP NOT NULL COMMENT '回本期',
  begin_at TIMESTAMP NOT NULL COMMENT '项目开始时间',
  end_at  TIMESTAMP NOT NULL COMMENT '项目结束时间',
  actual_begin_at TIMESTAMP COMMENT '实际开始时间',
  actual_end_at  TIMESTAMP COMMENT '实际结束时间',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);



DROP TABLE IF EXISTS project_resource;
CREATE TABLE project_resource (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL COMMENT '名称',
  contact_id INT NOT NULL COMMENT '联系人',
  remark VARCHAR(500) NOT NULL COMMENT '备注',
  ready_at  TIMESTAMP NOT NULL COMMENT '预计准备时间',
  actual_ready_at   TIMESTAMP COMMENT '实际准备时间',
  use_at  TIMESTAMP NOT NULL COMMENT '资源使用时间',
  actual_use_at  TIMESTAMP COMMENT '实际使用时间',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);


DROP TABLE IF EXISTS project_group;
CREATE TABLE project_group (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL COMMENT '成员',
  jobs VARCHAR(255) NOT NULL COMMENT '职务',
  is_leader INT NOT NULL DEFAULT 0 COMMENT '负责人1',
  remark VARCHAR(500) NOT NULL COMMENT '备注',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);


DROP TABLE IF EXISTS task;
CREATE TABLE task (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id INT NOT NULL COMMENT '项目',
  name VARCHAR(50) NOT NULL COMMENT '任务名称',
  remark VARCHAR(500) NOT NULL COMMENT '备注',
  begin_at TIMESTAMP NOT NULL COMMENT '任务开始时间',
  end_at  TIMESTAMP NOT NULL COMMENT '任务结束时间',
  buffer_at TIMESTAMP NOT NULL COMMENT '缓冲时间点＝开始时间＋缓冲时间',
  actual_buffer_at TIMESTAMP NOT NULL COMMENT '缓冲时间消耗点＝开始时间＋实际任务时间',
  actual_begin_at TIMESTAMP COMMENT '实际开始时间',
  actual_end_at  TIMESTAMP COMMENT '实际结束时间',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);

DROP TABLE IF EXISTS task_depend;
CREATE TABLE task_depend (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  main_id INT NOT NULL COMMENT '主任务',
  sub_id INT NOT NULL COMMENT '子任务'
);

-- 细分任务
DROP TABLE IF EXISTS side_task;
CREATE TABLE side_task (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  task_id INT NOT NULL COMMENT '主任务',
  name VARCHAR(50) NOT NULL COMMENT '任务名称',
  remark VARCHAR(500) NOT NULL COMMENT '备注',
  leader_id INT NOT NULL COMMENT '负责人',
  begin_at TIMESTAMP NOT NULL COMMENT '任务开始时间',
  end_at  TIMESTAMP NOT NULL COMMENT '任务结束时间',
  actual_begin_at TIMESTAMP COMMENT '实际开始时间',
  actual_end_at  TIMESTAMP COMMENT '实际结束时间',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);

-- 细分任务  需要的辅助人员 和时间
DROP TABLE IF EXISTS side_task_assist;
CREATE TABLE side_task_assist (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  side_task_id INT NOT NULL COMMENT '任务',
  assist_user_id INT NOT NULL COMMENT '辅助人员',
  begin_at TIMESTAMP NOT NULL COMMENT '开始时间',
  end_at  TIMESTAMP NOT NULL COMMENT '结束时间',
  actual_begin_at TIMESTAMP COMMENT '实际开始时间',
  actual_end_at  TIMESTAMP COMMENT '实际结束时间',
  remark VARCHAR(500) NOT NULL COMMENT '备注',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);


DROP TABLE IF EXISTS task_group;
CREATE TABLE task_group (
  id            BIGINT    unsigned   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL COMMENT '成员',
  jobs VARCHAR(255) NOT NULL COMMENT '职务',
  is_leader INT NOT NULL DEFAULT 0 COMMENT '负责人1',
  remark VARCHAR(500) NOT NULL COMMENT '备注',
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);


