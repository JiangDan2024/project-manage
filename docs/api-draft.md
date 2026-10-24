# 接口设计

## 一、通用约定

### 1.统一响应结构

**成功响应（code = 200）**
```json
{
  "code": 200,
  "msg": "ok",
  "data": {} //详细数据
}
```

**失败响应（code != 200）**
```json
{
  "code": 40001,
  "msg": "非法字段",
  "data": {}//errors
}
```

### 2.通用响应参数说明
| 字段 | 类型 | 说明 |
|:---|:---|:---|
| code | int | 业务状态码，0 表示成功，非 0 表示失败 |
| msg | string | 提示信息，成功时为 "ok"，失败时为具体错误原因 |
| data | object | 业务数据，失败时为 null |

### 3.通用错误码（放到这里，不用在下面接口里重复写）
| code | 含义 | 前端处理 |
|:---|:---|:---|
| 40001 | 非法字段 | 提示"筛选字段不合法" |
| 40002 | 非法操作符 | 提示"该字段不支持此操作" |
| 40301 | 无权限 | 提示"无访问权限" |

### 4.红线

- 前端不传userId，tenantId（租户id，如果用到的话）
- 前端只穿逻辑字段名（不暴露数据库的字段名称）
- 后端强制追加tenant_id（如果有的话）

### 5.通用请求头
除 `POST /api/auth/login` (登录) 接口外，所有接口**必须**在 Request Header 中携带以下字段：

| 请求头 | 请求内容 | 说明 |
|:---|:---|:---|
| Authorization | Bearer {token} | 登录后获取的 Token，用于身份校验 |
| Content-Type | application/json | 请求体格式（文件上传接口除外） |

### 6.通用工具

**查询通用类**
```java
public class BaseQueryReq {
    // 当前页码，默认第一页
    private Integer pageNum = 1;
    
    // 每页条数，默认十条
    private Integer pageSize = 10;
    
    // 全局模糊搜索关键字
    private String keyword;
}
```

## 二、RBAC实现

### 接口清单
| 模块 | 接口 | 方法 | 用途 | 状态 |

### 1.用户模块

#### 接口详情
#### 1. 查询用户列表 user/list

**接口说明**
- 接口功能
查询用户列表
- 接口请求地址
<http://localhost/system/user/list>

- 请求方式
GET
- 参数类型
JSON


**请求示例**
```json
{
    "pageNum":1,
    "pageSize":10,
    "keyword":"zhangsan",
    "phoneNumber":187****1882,
    "status":0
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| pageNum | 页码 | Integer | Y |
| pageSize | 每页数量 | Integer | Y |
| keyword | 关键字（模糊匹配用户名称、用户昵称） | String | N |
| phoneNumber | 电话 | String | N |
| status | 状态 | Integer | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    [
        {
            "userId": 1,
            "admin": true,
            "avatar": "",
            "createBy": "admin",
            "createTime": "2026-09-15 09:30:58",
            "delFlag": "0",
            "dept": {
                "ancestors": null,
                "children": [],
                "createBy": null,
                "createTime": null,
                "delFlag": null,
                "deptId": 103,
                "deptName": "研发部门",
                "email": null,
                "leader": "若依",
                "orderNum": null,
                "parentId": null,
                "parentName": null,
                "phone": null,
                "remark": null,
                "status": null,
                "updateBy": null,
                "updateTime": null
            },
            "deptId": 103,
            "email": "ry@163.com",
            "loginDate": "2026-10-22 15:48:13",
            "loginIp": "127.0.0.1",
            "nickName": "若依",
            "phonenumber": "15888888888",
            "postIds": null,
            "pwdUpdateDate": null,
            "remark": "管理员",
            "roleId": null,
            "roleIds": null,
            "roles": [],
            "sex": "1",
            "status": "0",
            "updateBy": null,
            "updateTime": null,
            "userName": "admin"
        }
    ]
  }
}
```

#### 2. 新增用户 user/add

**接口说明**
- 接口功能
管理员新增用户
- 接口请求地址
<http://localhost/system/user/add>

- 请求方式
POST
- 参数类型
JSON


**请求示例**
```json
{
    "userName": "admin",
    "nickName": "若依",
    "sex": "1",
    "phoneNumber": "15888888888",
    "remark": "管理员",
    "deptId": 103,
    "email": "ry@163.com",
    "password":"",
    "roles": []
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userName | 用户名 | String | Y |
| nickName | 用户昵称 | String | Y |
| sex | 性别 | Integer | N |
| phoneNumber | 电话 | String | N |
| remark | 备注 | String | N |
| deptId | 部门id | Integer | N |
| email | 邮箱 | String | N |
| password | 密码 | String | Y |
| roles | 角色id列表 | List<Integer> | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}//成功则没有data，失败返回errors
}
```

#### 3. 编辑用户 user/edit

**接口说明**
- 接口功能
管理员编辑用户
- 接口请求地址
<http://localhost/system/user/edit>

- 请求方式
PUT
- 参数类型
JSON


**请求示例**
```json
{
  "admin": false,
  "avatar": "",
  "createBy": "admin",
  "createTime": "2026-10-22 16:30:54",
  "delFlag": "0",
  "dept": null,
  "deptId": 103,
  "email": "d18735661882@126.com",
  "loginDate": null,
  "loginIp": "",
  "nickName": "测试",
  "password": "",
  "phonenumber": "18778781922",
  "postIds": [],
  "pwdUpdateDate": null,
  "remark": null,
  "roleId": null,
  "roleIds": [],
  "roles": [],
  "sex": "1",
  "status": "0",
  "updateBy": "",
  "updateTime": null,
  "userId": 100,
  "userName": "test"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
userId (必填) 及其他需要修改的字段

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}//成功则没有data，失败返回errors
}
```

#### 4. 根据用户ID查询 user/info

**接口说明**
- 接口功能
根据用户ID查询
- 接口请求地址
<http://localhost/system/user/info/{userId}>

- 请求方式
GET
- 参数类型
long/int


**请求示例**
<http://localhost/system/user/info/{100}>


**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userId | 用户ID | Integer | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
      "userId": 100,
      "admin": false,
      "avatar": "",
      "createBy": "admin",
      "createTime": "2026-10-22 16:30:54",
      "delFlag": "0",
      "dept": null,
      "deptId": null,
      "email": "",
      "loginDate": null,
      "loginIp": "",
      "nickName": "测试",
      "phonenumber": "",
      "postIds": null,
      "pwdUpdateDate": null,
      "remark": null,
      "roleId": null,
      "roleIds": null,
      "roles": [],
      "sex": "0",
      "status": "0",
      "updateBy": "",
      "updateTime": null,
      "userName": "test",
      "roleList": [
        {
          "roleId": 2,
          "admin": false,
          "createBy": null,
          "createTime": "2026-09-15 09:30:58",
          "dataScope": "2",
          "delFlag": "0",
          "deptCheckStrictly": true,
          "deptIds": null,
          "flag": false,
          "menuCheckStrictly": true,
          "menuIds": null,
          "permissions": null,
          "remark": "普通角色",
          "roleKey": "common",
          "roleName": "普通角色",
          "roleSort": 2,
          "status": "0",
          "updateBy": null,
          "updateTime": null
        }
      ]
  }
  //失败返回errors
}
```

#### 5. 删除用户 user/delete

**接口说明**
- 接口功能
根据用户ID查询
- 接口请求地址
<http://localhost/system/user/delete/{userIds}>

- 请求方式
DELETE
- 参数类型
long[]


**请求示例**
<http://localhost/system/user/delete/100,101>


**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userIds | 用户IDs | Long[] | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}//成功无data，失败返回errors
}
```

#### 6. 根据用户ID获取关联的角色 user/getAuthRole
**接口说明**
- 接口功能
根据用户ID获取关联的角色，返回角色清单
- 接口请求地址
<http://localhost/system/user/getAuthRole>

- 请求方式
GET
- 参数类型
json

**请求示例**
```json
{
  "pageSize": 10,
  "pageNum": 1,
  "keyword": "xxx",
  "status":"1"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userId | 用户Id | Integer | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "roles": [
        {
            "roleId": 2,
            "admin": false,
            "createBy": null,
            "createTime": "2026-09-15 09:30:58",
            "dataScope": "2",
            "delFlag": "0",
            "deptCheckStrictly": true,
            "deptIds": null,
            "flag": true,
            "menuCheckStrictly": true,
            "menuIds": null,
            "permissions": null,
            "remark": "普通角色",
            "roleKey": "common",
            "roleName": "普通角色",
            "roleSort": 2,
            "status": "0",
            "updateBy": null,
            "updateTime": null
        },
        {
            "roleId": 100,
            "admin": false,
            "createBy": null,
            "createTime": "2026-10-22 17:59:01",
            "dataScope": "1",
            "delFlag": "0",
            "deptCheckStrictly": true,
            "deptIds": null,
            "flag": false,
            "menuCheckStrictly": true,
            "menuIds": null,
            "permissions": null,
            "remark": null,
            "roleKey": "test",
            "roleName": "测试",
            "roleSort": 3,
            "status": "0",
            "updateBy": null,
            "updateTime": null
        }
    ],
    "user": {
        "userId": 2,
        "admin": false,
        "avatar": "",
        "createBy": "admin",
        "createTime": "2026-09-15 09:30:58",
        "delFlag": "0",
        "dept": {
            "ancestors": "0,100,101",
            "children": [],
            "createBy": null,
            "createTime": null,
            "delFlag": null,
            "deptId": 105,
            "deptName": "测试部门",
            "email": null,
            "leader": "若依",
            "orderNum": 3,
            "parentId": 101,
            "parentName": null,
            "phone": null,
            "remark": null,
            "status": "0",
            "updateBy": null,
            "updateTime": null
        },
        "deptId": 105,
        "email": "ry@qq.com",
        "loginDate": "2026-09-15 09:30:58",
        "loginIp": "127.0.0.1",
        "nickName": "若依",
        "phonenumber": "15666666666",
        "postIds": null,
        "pwdUpdateDate": "2026-09-15 09:30:58",
        "remark": "测试员",
        "roleId": null,
        "roleIds": null,
        "roles": [
            {
                "roleId": 2,
                "admin": false,
                "createBy": null,
                "createTime": null,
                "dataScope": "2",
                "delFlag": null,
                "deptCheckStrictly": false,
                "deptIds": null,
                "flag": false,
                "menuCheckStrictly": false,
                "menuIds": null,
                "permissions": null,
                "remark": null,
                "roleKey": "common",
                "roleName": "普通角色",
                "roleSort": 2,
                "status": "0",
                "updateBy": null,
                "updateTime": null
            }
        ],
        "sex": "1",
        "status": "0",
        "updateBy": "",
        "updateTime": null,
        "userName": "ry"
    }
  }
```

#### 7. 为用户授权角色 user/authRole
**接口说明**
- 接口功能
根据用户ID获取关联的角色，返回角色清单
- 接口请求地址
<http://localhost/system/user/authRole>

- 请求方式
POST
- 参数类型
JSON

**请求示例**
```json
{
    "userId":1,
    "roleIds":[1,2]
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userId | 用户Id | Integer | Y |
| roleIds | 角色ID列表 | List<Integer> | Y |

**响应示例**
```json
{
    "msg":"操作成功",
    "code":200,
    "data":{}//成功时无数据，失败返回error
}
```

#### 8. 修改状态 user/changeStatus
**接口说明**
- 接口功能
根据用户ID修改用户状态：停用/启用
- 接口请求地址
<http://localhost/system/user/changeStatus>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "userId":1,
    "status":"1"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userId | 用户Id | Integer | Y |
| status | 用户状态 | String | Y |

**响应示例**
```json
{
    "msg":"操作成功",
    "code":200,
    "data":{}//成功时无数据，失败返回error
}
```

#### 9. 获取部门树列表
**接口说明**
- 接口功能
查询部门树形结构数据，主要用于用户管理页面左侧的部门导航树，或新增/修改用户时选择所属部门。支持按部门名称或状态过滤。
- 接口请求地址
<http://localhost/system/user/deptTree>

- 请求方式
GET
- 参数类型
Query参数

**请求示例**
```text
http://localhost/system/user/deptTree?deptName=研发部&status=0
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| deptName | 部门名称（模糊匹配） | String | N |
| status | 部门状态（0正常 1停用） | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 100,
      "label": "若依科技",
      "children": [
        {
          "id": 101,
          "label": "深圳分公司",
          "children": [
            {
              "id": 105,
              "label": "研发部",
              "children": []
            }
          ]
        }
      ]
    }
  ]
}
```

**响应参数说明**
| 字段名 | 字段说明 | 字段类型 |
| id | 部门ID | Long |
| label | 部门名称 | String |
| children | 子部门列表（树形结构） | Array |

### 2.认证模块

#### 1. 用户登录 auth/login

**接口说明**
- 接口功能
用户提交账号、密码进行登录。校验通过后返回 JWT Token。
- 接口请求地址
<http://localhost/system/auth/login>

- 请求方式
POST
- 参数类型
JSON


**请求示例**
```json
{
  "userName": "admin",
  "password": "admin123"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userName | 用户名 | String | Y |
| password | 密码 | String | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6Im..."//成功返回token，失败返回error
  }
}
```

#### 2. 用户注册 auth/register

**接口说明**
- 接口功能
用户自助注册（仅获取基础账号，默认无任何业务权限）。
- 接口请求地址
<http://localhost/system/auth/register>

- 请求方式
POST
- 参数类型
JSON


**请求示例**
```json
{
  "userName": "zhangsan",
  "password": "Password123!",
  "nickName": "张三",
  "phoneNumber": "13800138000"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userName | 用户名 | String | Y |
| password | 密码 | String | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "注册成功，请联系管理员为您分配权限",
  "data": {}//成功无数据，失败error
}
```

#### 3.退出登录 auth/logout (还没思路)

#### 4.查看用户信息 auth/info
**接口说明**
- 接口功能
获取当前登录用户的信息、角色列表以及按钮权限点。前端接收到后，用于控制侧边栏菜单的显示以及页面内按钮的显隐。
- 接口请求地址
<http://localhost/system/auth/info>

- 请求方式
GET
- 参数类型
null


**请求示例**
null

**请求参数说明**

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "user": {
      "userId": 100,
      "userName": "admin",
      "nickName": "超级管理员",
      "avatar": ""
    },
    "roles": ["admin"],
    "permissions": [
      "system:user:list",
      "system:user:add",
      "project:list",
      "project:edit"
    ]
  }
}
```

### 3.角色模块

#### 1. 查询角色列表
**接口说明**
- 接口功能
分页查询角色列表
- 接口请求地址
<http://localhost/system/role/list>

- 请求方式
GET
- 参数类型
JSON


**请求示例**
```json
{
    "pageNum":1,
    "pageSize":10,
    "keyword":"zhangsan",
    "status":0
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| pageNum | 页码 | Integer | Y |
| pageSize | 每页数量 | Integer | Y |
| keyword | 关键字（模糊匹配角色名称，权限字符） | String | N |
| status | 状态 | Integer | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
     {
            "roleId": 1,
            "admin": true,
            "createBy": null,
            "createTime": "2026-09-15 09:30:58",
            "dataScope": "1",
            "delFlag": "0",
            "deptCheckStrictly": true,
            "deptIds": null,
            "flag": false,
            "menuCheckStrictly": true,
            "menuIds": null,
            "permissions": null,
            "remark": "超级管理员",
            "roleKey": "admin",
            "roleName": "超级管理员",
            "roleSort": 1,
            "status": "0",
            "updateBy": null,
            "updateTime": null
        },
  ]
}
```

#### 2. 新增角色
**接口说明**
- 接口功能
新增角色
- 接口请求地址
<http://localhost/system/role/add>

- 请求方式
POST
- 参数类型
JSON


**请求示例**
```json
{
    "roleName":"test",
    "roleKey":"test1",
    "roleSort":3,
    "status":"0",
    "menuIds":[1,100,1000],
    "deptIds":[],
    "menuCheckStrictly":true,
    "deptCheckStrictly":true
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleName | 角色名 | String | Y |
| roleKey | 权限字符 | String | Y |
| roleSort | 角色排序 | Integer | Y |
| status | 状态 | Integer | N |
| menuIds | 目录id清单 | list | N |
| deptIds | 部门id清单 | list | N |
| menuCheckStrictly | 菜单树选择项是否关联显示 | Boolean | N |
| deptCheckStrictly | 部门树选择项是否关联显示 | Boolean | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}//成功则没有data，失败返回errors
}
```

#### 3. 编辑角色
**接口说明**
- 接口功能
编辑角色
- 接口请求地址
<http://localhost/system/role/edit>

- 请求方式
PUT
- 参数类型
JSON


**请求示例**
```json
{
    "roleId":100,
    "admin":false,
    "createBy":null,
    "createTime":"2026-10-22 17:59:01",
    "dataScope":"1",
    "delFlag":"0",
    "deptCheckStrictly":true,
    "deptIds":null,
    "flag":false,
    "menuCheckStrictly":true,
    "menuIds":[1,100,1000,1001],
    "permissions":null,
    "remark":null,
    "roleKey":"test2",
    "roleName":"测试",
    "roleSort":3,
    "status":"0",
    "updateBy":null,
    "updateTime":null
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | String | Y |
| roleName | 角色名 | String | Y |
| roleKey | 权限字符 | String | Y |
| roleSort | 角色排序 | Integer | Y |
| status | 状态 | Integer | N |
| menuIds | 目录id清单 | list | N |
| deptIds | 部门id清单 | list | N |
| menuCheckStrictly | 菜单树选择项是否关联显示 | Boolean | N |
| deptCheckStrictly | 部门树选择项是否关联显示 | Boolean | N |
....

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}//成功则没有data，失败返回errors
}
```

#### 4. 修改角色状态
**接口说明**
- 接口功能
修改角色状态
- 接口请求地址
<http://localhost/system/role/changeStatus>

- 请求方式
PUT
- 参数类型
JSON


**请求示例**
```json
{
    "roleId":101,
    "status":"1"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | Integer | Y |
| status | 角色状态 | String | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}//成功则没有data，失败返回errors
}
```

#### 5. 根据角色ID查询角色信息

**接口说明**
- 接口功能
根据角色ID查询角色信息
- 接口请求地址
<http://localhost/system/role/info/{roleId}>

- 请求方式
GET
- 参数类型
long/int


**请求示例**
<http://localhost/system/role/info/{100}>


**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | Integer | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "menus":[
        {
            "children": [
                {
                    "children": [
                        {
                            "disabled": false,
                            "id": 1000,
                            "label": "用户查询"
                        }
                        ....
                    ],
                    "disabled": false,
                    "id": 100,
                    "label": "用户管理"
                }
                .....
            ]
        },
        ....
    ],
    "checkedKeys":[1,2,3...]
  }//失败返回errors
}
```

#### 6. 删除角色

**接口说明**
- 接口功能
删除角色
- 接口请求地址
<http://localhost/system/role/delete/{roleIds}>

- 请求方式
DELETE
- 参数类型
long[]


**请求示例**
<http://localhost/system/role/delete/100,101>


**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleIds | 角色IDs | Long[] | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}//成功无data，失败返回errors
}
```

#### 7. 查询该角色已分配的用户
**接口说明**
- 接口功能
根据角色ID查询该角色已分配的用户
- 接口请求地址
<http://localhost/system/role/authUser/allocatedList/pageNum=?&pageSize=?&roleId=?>

- 请求方式
GET
- 参数类型
pageNum=1&pageSize=10&roleId=2

**请求示例**
<http://localhost/system/role/authUser/allocatedList/pageNum=1&pageSize=10&roleId=2>


**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleID | 角色ID | Integer | Y |
| pageNum | 页码 | Integer | Y |
| pageSize | 每页数量 | Integer | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
            "userId": 2,
            "admin": false,
            "avatar": null,
            "createBy": null,
            "createTime": "2026-09-15 09:30:58",
            "delFlag": null,
            "dept": {
                "ancestors": null,
                "children": [],
                "createBy": null,
                "createTime": null,
                "delFlag": null,
                "deptId": 105,
                "deptName": null,
                "email": null,
                "leader": null,
                "orderNum": null,
                "parentId": null,
                "parentName": null,
                "phone": null,
                "remark": null,
                "status": null,
                "updateBy": null,
                "updateTime": null
            },
            "deptId": 105,
            "email": "ry@qq.com",
            "loginDate": null,
            "loginIp": null,
            "nickName": "若依",
            "phonenumber": "15666666666",
            "postIds": null,
            "pwdUpdateDate": null,
            "remark": null,
            "roleId": null,
            "roleIds": null,
            "roles": [],
            "sex": null,
            "status": "0",
            "updateBy": null,
            "updateTime": null,
            "userName": "ry"
        },
        ....
  ]
}
```

#### 8. 查询未分配用户角色列表
**接口说明**
- 接口功能
根据角色ID查询该角色已分配的用户
- 接口请求地址
<http://localhost/system/role/authUser/unallocatedList/pageNum=?&pageSize=?&roleId=?>

- 请求方式
GET
- 参数类型
pageNum=1&pageSize=10&roleId=2

**请求示例**
<http://localhost/system/role/authUser/unallocatedList/pageNum=1&pageSize=10&roleId=2>


**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleID | 角色ID | Integer | Y |
| pageNum | 页码 | Integer | Y |
| pageSize | 每页数量 | Integer | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
            "userId": 2,
            "admin": false,
            "avatar": null,
            "createBy": null,
            "createTime": "2026-09-15 09:30:58",
            "delFlag": null,
            "dept": {
                "ancestors": null,
                "children": [],
                "createBy": null,
                "createTime": null,
                "delFlag": null,
                "deptId": 105,
                "deptName": null,
                "email": null,
                "leader": null,
                "orderNum": null,
                "parentId": null,
                "parentName": null,
                "phone": null,
                "remark": null,
                "status": null,
                "updateBy": null,
                "updateTime": null
            },
            "deptId": 105,
            "email": "ry@qq.com",
            "loginDate": null,
            "loginIp": null,
            "nickName": "若依",
            "phonenumber": "15666666666",
            "postIds": null,
            "pwdUpdateDate": null,
            "remark": null,
            "roleId": null,
            "roleIds": null,
            "roles": [],
            "sex": null,
            "status": "0",
            "updateBy": null,
            "updateTime": null,
            "userName": "ry"
        },
        ....
  ]
}
```

#### 9. 批量选择用户授权
**接口说明**
- 接口功能
从未被分配的用户列表中批量选择用户授权
- 接口请求地址
<http://localhost/system/role/authUser/selectAll>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "roleId":1,
    "userIds":[1,2]
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | Integer | Y |
| userIds | 用户id清单 | list | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 10. 取消授权用户 
**接口说明**
- 接口功能
取消单个授权用户
- 接口请求地址
<http://localhost/system/role/authUser/cancel>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "roleId":1,
    "userId":1
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | Integer | Y |
| userId | 用户id | Integer | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 11. 批量取消授权用户
**接口说明**
- 接口功能
取消单个授权用户
- 接口请求地址
<http://localhost/system/role/authUser/cancelAll>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "roleId":1,
    "userIds":[1,2]
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | Integer | Y |
| userIds | 用户id清单 | list | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 12. 获取对应角色部门树列表
**接口说明**
- 接口功能
用于角色管理中分配“自定义数据权限”时，获取系统内所有的部门树，并返回该角色当前已经勾选/分配的部门ID集合。
- 接口请求地址
<http://localhost/system/role/deptTree/{roleId}>

- 请求方式
GET
- 参数类型
路径参数 (Path)

**请求示例**
```text
http://localhost/system/role/deptTree/2
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | Long | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "checkedKeys": [100, 101, 105],
    "depts": [
      {
        "id": 100,
        "label": "若依科技",
        "children": [
          {
            "id": 101,
            "label": "深圳分公司",
            "children": [
              {
                "id": 105,
                "label": "研发部",
                "children": []
              }
            ]
          }
        ]
      }
    ]
  }
}
```

**响应参数说明**
| 字段名 | 字段说明 | 字段类型 |
| checkedKeys | 该角色已勾选/分配的部门ID数组 | Array |
| depts | 全量部门树形结构数据（包含 id, label, children）| Array |

### 4. 部门模块

#### 1. 查询部门列表
**接口说明**
- 接口功能
查询部门列表（返回平铺列表）
- 接口请求地址
<http://localhost/system/dept/list>

- 请求方式
GET
- 参数类型
Query参数（拼接在URL后）

**请求示例**
```text
http://localhost/system/dept/list?deptName=研发部&status=0
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| deptName | 部门名称（模糊匹配） | String | N |
| status | 部门状态（0正常 1停用） | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "deptId": 100,
      "parentId": 0,
      "ancestors": "0",
      "deptName": "若依科技",
      "orderNum": 0,
      "leader": "若依",
      "phone": "15888888888",
      "email": "ry@qq.com",
      "status": "0",
      "delFlag": "0",
      "createBy": "admin",
      "createTime": "2026-09-15 09:30:58"
    },
    ....
  ]
}
```

#### 2. 查询部门列表（排除节点）
**接口说明**
- 接口功能
修改部门时，查询上级部门下拉树，需排除当前部门及其所有子部门，防止出现父节点选了自己的死循环问题。
- 接口请求地址
<http://localhost/system/dept/list/exclude/{deptId}>

- 请求方式
GET
- 参数类型
路径参数 (Path)

**请求示例**
```text
http://localhost/system/dept/list/exclude/100
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| deptId | 需要排除的部门ID | Long | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
        {
            "ancestors": "0,100,101",
            "children": [],
            "createBy": "admin",
            "createTime": "2026-09-15 09:30:58",
            "delFlag": "0",
            "deptId": 103,
            "deptName": "研发部门",
            "email": "ry@qq.com",
            "leader": "若依",
            "orderNum": 1,
            "parentId": 101,
            "parentName": null,
            "phone": "15888888888",
            "remark": null,
            "status": "0",
            "updateBy": null,
            "updateTime": null
        }
    ]
}
```

#### 3. 根据部门编号获取详细信息
**接口说明**
- 接口功能
根据部门ID查询部门详细信息（用于编辑页面数据回显）
- 接口请求地址
<http://localhost/system/dept/info/{deptId}>

- 请求方式
GET
- 参数类型
路径参数 (Path)

**请求示例**
```text
http://localhost/system/dept/info/100
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| deptId | 部门ID | Long | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "deptId": 100,
    "parentId": 0,
    "ancestors": "0",
    "deptName": "若依科技",
    "orderNum": 0,
    "leader": "若依",
    "phone": "15888888888",
    "email": "ry@qq.com",
    "status": "0",
    "delFlag": "0",
    "createBy": "admin",
    "createTime": "2026-09-15 09:30:58"
  }
}
```

#### 4. 新增部门
**接口说明**
- 接口功能
新增部门
- 接口请求地址
<http://localhost/system/dept/add>

- 请求方式
POST
- 参数类型
JSON

**请求示例**
```json
{
    "parentId": 100,
    "deptName": "深圳分公司",
    "orderNum": 1,
    "leader": "张三",
    "phone": "13800138000",
    "email": "zhangsan@qq.com",
    "status": "0"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| parentId | 父部门ID（0代表顶级） | Long | Y |
| deptName | 部门名称 | String | Y |
| orderNum | 显示排序 | Integer | Y |
| leader | 负责人 | String | N |
| phone | 联系电话 | String | N |
| email | 邮箱 | String | N |
| status | 部门状态（0正常 1停用） | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 5. 修改部门
**接口说明**
- 接口功能
修改部门信息
- 接口请求地址
<http://localhost/system/dept/edit>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "deptId": 101,
    "parentId": 100,
    "deptName": "深圳分公司-研发部",
    "orderNum": 1,
    "leader": "李四",
    "phone": "13900139000",
    "email": "lisi@qq.com",
    "status": "0"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| deptId | 部门ID | Long | Y |
| parentId | 父部门ID | Long | Y |
| deptName | 部门名称 | String | Y |
| orderNum | 显示排序 | Integer | Y |
| leader | 负责人 | String | N |
| phone | 联系电话 | String | N |
| email | 邮箱 | String | N |
| status | 部门状态（0正常 1停用） | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 6. 保存部门排序
**接口说明**
- 接口功能
拖拽或修改部门列表时的排序保存（批量更新）
- 接口请求地址
<http://localhost/system/dept/updateSort>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "deptIds": "100,101,102",
    "orderNums": "0,1,2"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| deptIds | 部门ID清单（逗号分隔的字符串）| String | Y |
| orderNums | 对应的排序号清单（逗号分隔）| String | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 7. 删除部门
**接口说明**
- 接口功能
删除部门。如果存在下级部门或部门下存在用户，则不允许删除，返回警告提示。
- 接口请求地址
<http://localhost/system/dept/delete/{deptId}>

- 请求方式
DELETE
- 参数类型
路径参数 (Path)

**请求示例**
```text
http://localhost/system/dept/101
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| deptId | 部门ID | Long | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

### 5. 菜单模块

#### 1. 查询菜单列表
**接口说明**
- 接口功能
查询菜单列表（通常返回平铺列表，前端通过树形组件组合展示）
- 接口请求地址
<http://localhost/system/menu/list>

- 请求方式
GET
- 参数类型
Query参数

**请求示例**
```text
http://localhost/system/menu/list?menuName=用户管理&status=0
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| menuName | 菜单名称（模糊匹配） | String | N |
| status | 菜单状态（0正常 1停用） | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "menuId": 1,
      "menuName": "系统管理",
      "parentId": 0,
      "orderNum": 1,
      "path": "system",
      "component": null,
      "isFrame": "1",
      "isCache": "0",
      "menuType": "M",
      "visible": "0",
      "status": "0",
      "perms": "",
      "icon": "system",
      "createBy": "admin",
      "createTime": "2026-09-15 09:30:58"
    }
  ]
}
```

#### 2. 根据菜单编号获取详细信息
**接口说明**
- 接口功能
根据菜单ID查询菜单详细信息（用于编辑回显）
- 接口请求地址
<http://localhost/system/menu/info/{menuId}>

- 请求方式
GET
- 参数类型
路径参数 (Path)

**请求示例**
```text
http://localhost/system/menu/info/1
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| menuId | 菜单ID | Long | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "menuId": 1,
    "menuName": "系统管理",
    "parentId": 0,
    "orderNum": 1,
    "path": "system",
    "component": null,
    "isFrame": "1",
    "isCache": "0",
    "menuType": "M",
    "visible": "0",
    "status": "0",
    "perms": "",
    "icon": "system",
    "createBy": "admin",
    "createTime": "2026-09-15 09:30:58"
  }
}
```

#### 3. 获取菜单下拉树列表
**接口说明**
- 接口功能
用于在其他模块（如新增菜单、选择上级菜单等）展示树形下拉框
- 接口请求地址
<http://localhost/system/menu/treeselect>

- 请求方式
GET
- 参数类型
Query参数

**请求示例**
```text
http://localhost/system/menu/treeselect
```

**请求参数说明**
无（或可传菜单对象做简单过滤）

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 1,
      "label": "系统管理",
      "children": [
        {
          "id": 100,
          "label": "用户管理",
          "children": []
        }
      ]
    }
  ]
}
```

#### 4. 加载对应角色菜单列表树
**接口说明**
- 接口功能
在角色管理分配权限时，获取所有菜单树，并返回该角色已经勾选的菜单ID集合
- 接口请求地址
<http://localhost/system/menu/roleMenuTreeselect/{roleId}>

- 请求方式
GET
- 参数类型
路径参数 (Path)

**请求示例**
```text
http://localhost/system/menu/roleMenuTreeselect/2
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| roleId | 角色ID | Long | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "checkedKeys": [100, 1001, 1002], 
    "menus": [
      {
        "id": 1,
        "label": "系统管理",
        "children": [
          {
            "id": 100,
            "label": "用户管理",
            "children": []
          }
        ]
      }
    ]
  }
}
```

#### 5. 新增菜单
**接口说明**
- 接口功能
新增菜单。包含校验：菜单名称唯一、外链必须以http(s)开头、路由名称或地址唯一等。
- 接口请求地址
<http://localhost/system/menu/add>

- 请求方式
POST
- 参数类型
JSON

**请求示例**
```json
{
    "parentId": 0,
    "menuName": "项目管理",
    "orderNum": 2,
    "path": "project",
    "isFrame": "1",
    "isCache": "0",
    "menuType": "M",
    "visible": "0",
    "status": "0",
    "icon": "project"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| parentId | 父菜单ID（0代表顶级） | Long | Y |
| menuName | 菜单名称 | String | Y |
| orderNum | 显示排序 | Integer | Y |
| path | 路由地址 | String | Y |
| component | 组件路径（按钮类型无此字段） | String | N |
| isFrame | 是否为外链（0是 1否） | String | N |
| isCache | 是否缓存（0缓存 1不缓存）| String | N |
| menuType | 菜单类型（M目录 C菜单 F按钮）| String | Y |
| visible | 显示状态（0显示 1隐藏） | String | N |
| status | 菜单状态（0正常 1停用） | String | N |
| perms | 权限标识（如 `system:user:list`）| String | N |
| icon | 菜单图标 | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 6. 修改菜单
**接口说明**
- 接口功能
修改菜单信息。包含校验同新增，且上级菜单不能选择自己。
- 接口请求地址
<http://localhost/system/menu/edit>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "menuId": 100,
    "parentId": 0,
    "menuName": "项目管理",
    "orderNum": 2,
    "path": "project",
    "menuType": "M",
    "visible": "0",
    "status": "0"
}
```

**请求参数说明**
与新增参数一致，且 `menuId` 必传。

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 7. 保存菜单排序
**接口说明**
- 接口功能
拖拽或修改菜单列表时的排序保存（批量更新）
- 接口请求地址
<http://localhost/system/menu/updateSort>

- 请求方式
PUT
- 参数类型
JSON

**请求示例**
```json
{
    "menuIds": "1,2,3",
    "orderNums": "0,1,2"
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| menuIds | 菜单ID清单（逗号分隔的字符串）| String | Y |
| orderNums | 对应的排序号清单（逗号分隔）| String | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 8. 删除菜单
**接口说明**
- 接口功能
删除菜单。如果存在子菜单或已分配给角色，则不允许删除，返回警告提示。
- 接口请求地址
<http://localhost/system/menu/delete/{menuId}>

- 请求方式
DELETE
- 参数类型
路径参数 (Path)

**请求示例**
```text
http://localhost/system/menu/delete/100
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| menuId | 菜单ID | Long | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

## 三、变更历史

### 1.变更历史记录

#### 1. 批量新增历史记录
**接口说明**
- 接口功能
当业务表编辑时，自动触发新增历史记录(后端自动触发，无需前端)
- 接口请求地址
<http://localhost/system/history/add>

- 请求方式
POST
- 参数类型
JSON

**请求示例**
```json
[
  {
      "entityName": "project",
      "entityId": 1,
      "property": "name",
      "oldValue": "old",
      "newValue": "new",
      "operator": "admin",
      "source": "MANUAL",
      "remark": "xxx",
      "modifyTime": "0"
  }
]

```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| entityName | 实体类名称 | Long | Y |
| entityId | 实体类的id | String | Y |
| property | 实体类的属性 | Integer | Y |
| oldValue | 旧值 | String | Y |
| newValue | 新值 | String | N |
| operator | 修改人 | String | N |
| source | 来源 | String | N |
| remark | 备注 | String | Y |
| modifyTime | 更新时间 | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 2. 根据实体类名和实体类id查询对应的变更记录
**接口说明**
- 接口功能
根据实体类名和实体类id查询对应的变更记录
- 接口请求地址
<http://localhost/system/history/search>

- 请求方式
GET
- 参数类型
JSON

**请求示例**
```json
{
    "entityName": "project",
    "entityId": 1
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| entityName | 实体类名称 | Long | Y |
| entityId | 实体类的id | String | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
        "entityName": "project",
        "entityId": 1,
        "property": "name",
        "oldValue": "old",
        "newValue": "new",
        "operator": "admin",
        "source": "MANUAL",
        "remark": "xxx",
        "modifyTime": "0"
    },
    ....
  ]
}
```

## 四、自定义表格

### 1.表格样式记录

#### 1. 批量新增/编辑表格宽度
**接口说明**
- 接口功能
前端用户拖拽表格宽度时，自动保存表格宽度（第一次保存即新增）
- 接口请求地址
<http://localhost/system/table/saveWidth>

- 请求方式
POST
- 参数类型
JSON

**请求示例**
```json
[
  {
    "userId":1,
    "tableCode":"project",
    "columnField":"name",
    "columnWidth":100,
    "createTime":"2026-09-10 10:10",
    "updateTime":"2026-09-10 10:10"
  }
  ......
]
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userId | 用户ID | Long | Y |
| tableCode | 实体类标识 | String | Y |
| columnField | 列名 | Integer | Y |
| columnWidth | 列宽 | String | Y |
| createTime | 创建时间 | String | N |
| updateTime | 更新时间 | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```


#### 2. 批量新增/编辑列顺序和是否展示
**接口说明**
- 接口功能
前端用户可以自行设置列顺序和是否展示
- 接口请求地址
<http://localhost/system/table/saveOthers>

- 请求方式
POST
- 参数类型
JSON

**请求示例**
```json
[
  {
    "userId":1,
    "tableCode":"project",
    "columnField":"name",
    "columnSort":1,
    "columnShowFlag":1,
    "createTime":"2026-09-10 10:10",
    "updateTime":"2026-09-10 10:10"
  }
  ......
]
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userId | 用户ID | Long | Y |
| tableCode | 实体类标识 | String | Y |
| columnField | 列名 | Integer | Y |
| columnSort | 列顺序 | String | Y |
| columnShowFlag | 列是否展示 | String | Y |
| createTime | 创建时间 | String | N |
| updateTime | 更新时间 | String | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

#### 3. 根据用户ID和实体类标识CODE查询对应页面的表格自定义设置
**接口说明**
- 接口功能
根据用户ID和实体类标识CODE查询对应页面的表格自定义设置
- 接口请求地址
<http://localhost/system/table/search>

- 请求方式
GET
- 参数类型
JSON

**请求示例**
```json
{
    "userId": "project",
    "tableCode": 1
}
```

**请求参数说明**
| 字段名 | 字段说明 | 字段类型 | 是否必填 |
| userId | 用户ID | Long | Y |
| tableCode | 实体类标识 | String | Y |

**响应示例**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "userId":1,
      "tableCode":"project",
      "columnField":"name",
      "columnName":"姓名",
      "columnSort":1,
      "columnShowFlag":1,
      "columnWidth":100,
      "createTime":"2026-09-10 10:10",
      "updateTime":"2026-09-10 10:10"
    }
    ......
  ]
}
```

## 五、自定义查询

### 1.查询视图

### 2.查询条件明细

## 六、业务表

### 1.项目

#### 1. 分页查询项目列表
**接口说明**
- 请求地址：`<http://localhost/biz/project/list>`
- 请求方式：`POST`
- 参数类型：`JSON`

**请求示例**
```json
{
  "pageNum": 1,
  "pageSize": 10,
  "keyword": "智能仓储",
  "status": 1,
  "priority": 1
}
```
**请求参数说明**
| 字段名 | 字段说明 | 类型 | 必填 |
| pageNum | 页码 | Integer | Y |
| pageSize | 每页条数 | Integer | Y |
| keyword | 全局关键字（项目编号或名称） | String | N |
| status | 状态（1进行中 2已完成 3已暂停 4已关闭）| Integer | N |
| priority | 优先级（1高 2中 3低） | Integer | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "total": 1,
    "list": [
      {
        "id": 1,
        "projectNo": "P2026001",
        "projectName": "智能仓储改造",
        "status": 1,
        "priority": 1,
        "managerId": 1001,
        "budget": 850000.00,
        "startDate": "2026-01-10",
        "endDate": "2026-06-30",
        "progress": 65
      }
    ]
  }
}
```

#### 2. 根据ID查询项目详情
**接口说明**
- 请求地址：`<http://localhost/biz/project/info/{id}>`
- 请求方式：`GET`
- 参数类型：路径参数

**请求示例**
```text
http://localhost/biz/project/info/1
```

**响应示例**
```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "id": 1,
    "projectNo": "P2026001",
    "projectName": "智能仓储改造",
    "status": 1,
    "priority": 1,
    "managerId": 1001,
    "budget": 850000.00,
    "startDate": "2026-01-10",
    "endDate": "2026-06-30",
    "progress": 65,
    "remark": null
  }
}
```

#### 3. 新增项目
**接口说明**
- 请求地址：`<http://localhost/biz/project/add>`
- 请求方式：`POST`
- 参数类型：`JSON`

**请求示例**
```json
{
  "projectNo": "P2026006",
  "projectName": "新工厂规划",
  "status": 1,
  "priority": 1,
  "managerId": 1002,
  "budget": 500000.00,
  "startDate": "2026-11-01",
  "endDate": "2027-05-01"
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```

#### 4. 修改项目
**接口说明**
- 请求地址：`<http://localhost/biz/project/edit>`
- 请求方式：`PUT`
- 参数类型：`JSON`

**请求示例**
```json
{
  "id": 1,
  "projectName": "智能仓储改造(一期)",
  "progress": 80
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```

#### 5. 批量删除项目
**接口说明**
- 请求地址：`<http://localhost/biz/project/delete>`
- 请求方式：`DELETE`
- 参数类型：`JSON`

**请求示例**
```json
{
  "ids": [1, 2, 3]
}
```

**请求参数说明**
| 字段名 | 字段说明 | 类型 | 必填 |
| ids | 项目ID数组 | Array | Y |

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```


---

### 2.任务

#### 1. 分页查询任务列表
**接口说明**
- 请求地址：`<http://localhost/biz/task/list>`
- 请求方式：`POST`
- 参数类型：`JSON`

**请求示例**
```json
{
  "pageNum": 1,
  "pageSize": 10,
  "keyword": "联调",
  "projectId": 1,
  "status": 1
}
```
**请求参数说明**
| 字段名 | 字段说明 | 类型 | 必填 |
| pageNum | 页码 | Integer | Y |
| pageSize | 每页条数 | Integer | Y |
| keyword | 关键字（标题或编号） | String | N |
| projectId | 关联项目ID | Long | N |
| status | 状态（0待开始 1进行中 2已完成 3已取消）| Integer | N |
| priority | 优先级（1高 2中 3低） | Integer | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "total": 1,
    "list": [
      {
        "id": 1,
        "taskNo": "T20261001",
        "taskTitle": "仓储接口联调",
        "projectId": 1,
        "assigneeId": 1001,
        "taskType": "DEV",
        "priority": 1,
        "status": 1,
        "dueDate": "2026-10-20",
        "estimateHours": 16.0,
        "actualHours": 10.0,
        "tags": "接口,联调"
      }
    ]
  }
}
```

#### 2. 根据ID查询任务详情
**接口说明**
- 请求地址：`<http://localhost/biz/task/info/{id}>`
- 请求方式：`GET`

**请求示例**
```text
http://localhost/biz/task/info/1
```

#### 3. 新增任务
**接口说明**
- 请求地址：`<http://localhost/biz/task/add>`
- 请求方式：`POST`
- 参数类型：`JSON`

**请求示例**
```json
{
  "taskNo": "T20261007",
  "taskTitle": "仓储前端页面开发",
  "projectId": 1,
  "assigneeId": 1003,
  "taskType": "DEV",
  "priority": 2,
  "status": 0,
  "dueDate": "2026-10-30",
  "estimateHours": 20.0
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```

#### 4. 修改任务
**接口说明**
- 请求地址：`<http://localhost/biz/task/edit>`
- 请求方式：`PUT`
- 参数类型：`JSON`

**请求示例**
```json
{
  "id": 1,
  "status": 2,
  "actualHours": 15.0
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```

#### 5. 批量删除任务
**接口说明**
- 请求地址：`<http://localhost/biz/task/delete>`
- 请求方式：`DELETE`
- 参数类型：`JSON`

**请求示例**
```json
{
  "ids": [1, 2]
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```


---

### 3.物料主数据

#### 1. 分页查询物料列表
**接口说明**
- 请求地址：`<http://localhost/biz/material/list>`
- 请求方式：`POST`
- 参数类型：`JSON`

**请求示例**
```json
{
  "pageNum": 1,
  "pageSize": 10,
  "keyword": "钢板",
  "categoryId": 101,
  "materialType": 1,
  "status": 1
}
```
**请求参数说明**
| 字段名 | 字段说明 | 类型 | 必填 |
| pageNum | 页码 | Integer | Y |
| pageSize | 每页条数 | Integer | Y |
| keyword | 关键字（编码或名称） | String | N |
| categoryId | 物料分类ID | Long | N |
| materialType | 类型（1原材料 2半成品 3成品 4辅料）| Integer | N |
| status | 状态（1启用 2停用 3待审核 4已作废）| Integer | N |

**响应示例**
```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "total": 1,
    "list": [
      {
        "id": 1,
        "materialNo": "M-1001",
        "materialName": "冷轧钢板",
        "categoryId": 101,
        "materialType": 1,
        "spec": "SPCC 1.0×1250×2500",
        "unit": "KG",
        "status": 1,
        "standardPrice": 5.2000,
        "safetyStock": 2000.00,
        "shelfLife": null,
        "supplierId": 2001,
        "version": "A",
        "effectiveDate": "2025-01-01"
      }
    ]
  }
}
```

#### 2. 根据ID查询物料详情
**接口说明**
- 请求地址：`<http://localhost/biz/material/info/{id}>`
- 请求方式：`GET`

**请求示例**
```text
http://localhost/biz/material/info/1
```

#### 3. 新增物料
**接口说明**
- 请求地址：`<http://localhost/biz/material/add>`
- 请求方式：`POST`
- 参数类型：`JSON`

**请求示例**
```json
{
  "materialNo": "M-1004",
  "materialName": "碳素结构钢",
  "categoryId": 101,
  "materialType": 1,
  "spec": "Q235 2.0×1500×3000",
  "unit": "KG",
  "status": 1,
  "standardPrice": 4.8000,
  "safetyStock": 1500.00,
  "version": "A"
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```

#### 4. 修改物料
**接口说明**
- 请求地址：`<http://localhost/biz/material/edit>`
- 请求方式：`PUT`
- 参数类型：`JSON`

**请求示例**
```json
{
  "id": 1,
  "standardPrice": 5.5000,
  "status": 2
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```

#### 5. 批量删除物料
**接口说明**
- 请求地址：`<http://localhost/biz/material/delete>`
- 请求方式：`DELETE`
- 参数类型：`JSON`

**请求示例**
```json
{
  "ids": [1, 2]
}
```

**响应示例**
```json
{ "code": 200, "msg": "操作成功", "data": null }
```
