### 权限项目接口文档

#### 1.用户模块

##### 1.1 用户登陆

接口说明：用户登陆系统的接口

接口地址：/rbac/user/current/login

请求方式：POST

请求参数：

| 请求参数     | 参数类型   | 参数说明 |
| -------- | ------ | ---- |
| username | string | 用户名  |
| userPassword | string | 密码   |

返回结果：

```json
{
  "code": 0, //编码
  "success": true, //请求成功标志
  "msg": null, //消息
  "data": "11rdrEwT3KgbVtxPdgLlrszJskUrJjtp76wmg1emWa16+2D+yjVnNF8mVOd9oAjXMIRUAw2oLoZXH5r+O0v7dA==" //token
}
```

##### 1.2 修改用户的密码

接口说明：修改所有用户的密码

接口地址：/rbac/user/changePass

请求方式：POST

请求参数：

| 请求参数     | 参数类型   | 参数说明        |
|----------| ------ | ----------- |
| id        | string | 需要被修改的用户的id |
| password | string | 新密码         |

返回结果：

```json
{
  "code": 0, //编码
  "success": true, //请求成功标志
  "msg": null, //消息
  "data": "11rdrEwT3KgbVtxPdgLlrszJskUrJjtp76wmg1emWa16+2D+yjVnNF8mVOd9oAjXMIRUAw2oLoZXH5r+O0v7dA==" //token
}
```

##### 1.3 修改自己的密码

接口说明：用户修改自己的密码，所有登陆之后的用户都具有的权限

接口地址：/rbac/user/current/changePass

请求方式：POST

请求参数：

| 请求参数    | 参数类型   | 参数说明 |
| ------- | ------ | ---- |
| oldPass | string | 旧密码  |
| newPass | string | 新密码  |

返回结果：

```json
{
  "code": 0, //编码
  "success": true, //请求成功标志
  "msg": null, //消息
  "data": "11rdrEwT3KgbVtxPdgLlrszJskUrJjtp76wmg1emWa16+2D+yjVnNF8mVOd9oAjXMIRUAw2oLoZXH5r+O0v7dA==" //token
}
```

##### 1.4 显示自己的详情,包括菜单，功能，角色

接口说明：可以获取当前登录用户自己的信息，包括菜单，功能，角色等(菜单功能树形结构，前端人员自己根据parentId组装)

接口地址：/rbac/user/current/get

请求方式：GET

请求参数：无

```json
{
 "code": 0, //编码
 "success": true,//请求成功标志
 "msg": null,//消息
 "data": {
  "menFc": [ //功能
   {
    "fcCode": "rbac:user:manager", //功能编码
    "fcTitle": "用户管理", //功能标题
    "menuId": "rbac:menu:user:manager", //菜单id
    "fcStatus": "ENABLE" //功能状态
   }
  ],
  "menus": [
   {
    "menuCode": "rbac:menu:system:manager", //菜单编码
    "menuTitle": "系统管理", //菜单标题
    "menuType": "FOLDER", //菜单类型
    "parentId": null, //父级id
    "menuOrder": 1.0, //菜单排序
    "menuIcon": "icon_sys_manager", //菜单图标
    "menuStatus": "ENABLE" //菜单状态
   }
  ],
  "roles": [
   {
    "id": "78827eacf44c45f691c2307a6011e3d0", //角色id
    "updateTime": "2024-06-14T01:00:27.426+00:00", //更新时间
    "createTime": "2024-06-14T01:00:27.426+00:00",    //创建时间
    "roleName": "超级管理员", //角色名称
    "roleStatus": true, //角色状态
    "roleRemark": null,   //角色备注
    "admin": true //是否是管理员
   }
  ],
  "user": {
   "id": "82ca1667d5544e9d8d2661b7bbab8066", //用户id
   "updateTime": "2024-06-14T01:00:27.471+00:00", //更新时间
   "createTime": "2024-06-14T01:00:27.471+00:00", //创建时间
   "userName": "admin", //用户名
   "userStatus": true, //用户状态
   "nickName": "admin" //昵称
  }
 }
}

```
##### 1.5 获取用户的信息详情 包括角色

接口说明：获取用户的信息详情

接口地址：/rbac/user/get

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| id   | String | 用户id |

```json
{
 "code": 0, //编码
 "success": true,//请求成功标志
 "msg": null,//消息
 "data": {
  "roles": [
   {
    "id": "78827eacf44c45f691c2307a6011e3d0", //角色id
    "updateTime": "2024-06-14T01:00:27.426+00:00", //更新时间
    "createTime": "2024-06-14T01:00:27.426+00:00",    //创建时间
    "roleName": "超级管理员", //角色名称
    "roleStatus": true, //角色状态
    "roleRemark": null,   //角色备注
    "admin": true //是否是管理员
   }
  ],
  "user": {
   "id": "82ca1667d5544e9d8d2661b7bbab8066", //用户id
   "updateTime": "2024-06-14T01:00:27.471+00:00", //更新时间
   "createTime": "2024-06-14T01:00:27.471+00:00", //创建时间
   "userName": "admin", //用户名
   "userStatus": true, //用户状态
   "nickName": "admin" //昵称
  }
 }
}
```

##### 1.6 修改自己的个人信息

接口说明：修改自己的基础信息

接口地址：/rbac/user/current/update

请求方式：POST

请求参数：

| 请求参数     | 参数类型   | 参数说明 |
|----------|--------| ---- |
 | nickName | String | 昵称  |


返回结果：

```json
{
  "code": 0, //编码
  "success": true, //请求成功标志
  "msg": null, //消息
  "data": null //数据
}
```

##### 1.7 修改用户的信息

接口说明：修改所有用户的基础信息

接口地址：/rbac/user/update

请求方式：POST

请求参数：

| 请求参数     | 参数类型   | 参数说明 |
|----------|--------|------|
| id       | String | 用户   |
| nickName | String | 昵称   |


返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 1.8 禁用用户

接口说明：禁用用户

接口地址：/rbac/user/disable

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| id   | String | 用户id |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 1.9 启用用户

接口说明：禁用用户

接口地址：/rbac/user/enable

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| id   | String | 用户id |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```



##### 1.11 查询用户

接口说明：查询用户

接口地址：/rbac/user/query

请求方式：POST

请求参数：

| 请求参数     | 参数类型    | 参数说明 |
|----------|---------|------|
| username | String  | 用户名  |
| nickName | String  | 昵称   |
| page     | Integer | 页码   |
| size     | Integer | 每页数量 |

返回结果：

```json
{
    "code": 0, //编码
    "success": true, //请求成功标志
    "msg": null, //消息
    "data": { //数据
        "content": [ 
            {
                "id": "2", //用户id
                "updateTime": "2024-06-14T01:00:27.471+00:00", //更新时间
                "createTime": "2024-06-14T01:00:27.471+00:00", //创建时间
                "userName": "大王", //用户名
                "userStatus": true, //用户状态
                "nickName": "admin" //昵称
            }
        ],
        "pageable": {
            "sort": {
                "empty": true, //是否为空
                "unsorted": true, //是否排序
                "sorted": false //是否排序
            },
            "offset": 1, //偏移量
            "pageSize": 1, //每页数量
            "pageNumber": 1, //页码
            "unpaged": false, //是否分页
            "paged": true //是否分页
        },
        "totalPages": 15, //总页数
        "totalElements": 15, //总数量
        "last": false, //是否最后一页
        "number": 1, //页码
        "size": 1, //每页数量
        "sort": {
            "empty": true, //是否为空
            "unsorted": true, //是否排序
            "sorted": false //是否排序
        },
        "numberOfElements": 1, //当前页数量
        "first": false, //是否第一页
        "empty": false //是否为空
    }
}
```


##### 1.12 授权用户角色

接口说明：授权用户角色吗,角色一次性可以分配多个

接口地址：/rbac/user/grantRole

请求方式：POST

查询参数:

| 请求参数    | 参数类型       | 参数说明  |
|---------|------------|-------|
| userId  | String     | 用户id  |
| roleIds | String []  | 角色i数组 |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 1.13 创建用户

接口说明：创建用户

接口地址：/rbac/user/add

请求方式：POST

请求参数：

| 请求参数     | 参数类型   | 参数说明 |
|----------|--------|------|
| username | String | 用户名  |
| userPassword | String | 密码   |
| nickName | String | 昵称   |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

#####  删除用户

接口说明：删除用户

接口地址：/rbac/user/removeUser



#### 2.菜单模块

##### 2.1 获取所有的菜单

接口说明：获取所有的菜单，数据组装成

接口地址：/rbac/menu/list

请求方式：GET

请求参数：无

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": {
  "menuFc": [
   {
    "fcCode": "rbac:menu:edit", //功能编码
    "fcTitle": "菜单编辑", //功能标题
    "menuId": "rbac:menu:menu:manager", //菜单id
    "fcStatus": "ENABLE" //功能状态
   }
  ],
  "menus": [
  
   {
    "menuCode": "rbac:menu:menu:manager", //菜单编码
    "menuTitle": "菜单管理", //菜单标题
    "menuType": "PAGE", //菜单类型
    "parentId": "rbac:menu:system:manager", //父级id
    "menuOrder": 3.0, //菜单排序
    "menuIcon": "icon_sys_manager_menu", //菜单图标
    "menuStatus": "ENABLE" //菜单状态
   }
  ]
 }
}
```


##### 2.2 修改菜单

接口说明：修改菜单的信息，只有几个可以被修改的信息允许编辑

接口地址：/rbac/menu/update

请求方式：POST

请求参数：

| 请求参数     | 参数类型   | 参数说明 |
|----------|--------|------|
| menuCode | String | 菜单编码 |
| menuTitle | String | 菜单标题 |
| menuOrder | Float  | 菜单排序 |
| menuIcon | String | 菜单图标 |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 2.3 禁用菜单

接口说明：禁用菜单

接口地址：/rbac/menu/disableMenu

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| menuCode | String | 菜单编码 |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```



##### 2.4 启用菜单

接口说明：启用菜单

接口地址：/rbac/menu/enableMenu

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| menuCode | String | 菜单编码 |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```



##### 2.5 禁用功能

接口说明：禁用功能

接口地址：/rbac/menu/disableFunc

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| fcCode | String | 功能编码 |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```



##### 2.6 启用功能

接口说明：启用功能

接口地址：/rbac/menu/enableFunc

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| fcCode | String | 功能编码 |

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 2.7 获取菜单的信息

接口说明，根据菜单编码获取菜单信息

接口地址：/rbac/menu/get

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| id   | String | 菜单编码 |

返回结果：

```json
{
 "code": 0,
 "success": true,
 "msg": null,
 "data": {
  "menuCode": "rbac:menu:vedio:manager",//菜单编码
  "menuTitle": "菜单管理", //菜单名称
  "menuType": "PAGE", //菜单类型
  "parentId": "rbac:menu:morrly:manager",//父级id
  "menuOrder": 0.0,//排序
  "menuIcon": "qqq",//图标
  "menuStatus": "ENABLE"//状态
 }
}
```


#### 3.角色模块

##### 3.1 查询所有角色

接口说明：查询所有的角色

接口地址：/rbac/role/query

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明                                                                                           |
|------|--------|------------------------------------------------------------------------------------------------|
| pageNo | String | 页码（不传默认为1）                                                                                     |
| pageSize | String | 显示条数 （不传默认为20）                                                                                 |
| sort | String | 排序 （不穿默认排序，传值方式如单个 createTime:+ 多个用逗号分隔如 createTime:asc,updateTime:desc） |
| roleName | String | 角色名称（不传查询所有）                                                                                   |


返回结果：
```json
{
    "code": 0,
    "success": true,
    "msg": null,
    "data": {
        "content": [
            {
                "id": "928c971414ce49b29f94b3f586afd29f", //角色ID
                "updateTime": 1718850517171, //更新时间
                "createTime": 1718850517171, //创建时间
                "roleName": "超级管理员",//角色名称
                "roleStatus": "ENABLE",//角色状态 ENABLE 启用  DISABLE 禁用
                "roleRemark": null, //备注
                "admin": true //是否为admin用户 true 是  false 否
            }
        ],
        "pageable": {
            "sort": {
                "empty": true, //是否为空
                "sorted": false, //是否未排序
                "unsorted": true //是否排序
            },
            "offset": 0, //偏移量
            "pageSize": 10, //每页数量
            "pageNumber": 0,  //页码
            "unpaged": false, //是否未分页
            "paged": true //是否分页
        },
        "totalPages": 1, //总页数
        "last": true, //是否最后一页
        "totalElements": 1, //总数
        "number": 0, //当前页
        "size": 10, //显示条数
        "sort": { //每页数量
            "empty": true, //是否为空
            "sorted": false, //是否未排序
            "unsorted": true //是否排序
        },
        "numberOfElements": 1, //当前页数量
        "first": true, //是否第一页
        "empty": false //是否为空
    }
}
```
接口说明：根据单个角色信息

接口地址：/rbac/role/get

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| roleId| String | 角色ID|


返回结果：
```json
{
  "code": 0,
  "success": true,
  "msg": null,
  "data": {
    "id": "84098be57d244e85bbf93849e0b7a741",//角色id
    "updateTime": 1718851592738,//更新时间
    "createTime": 1718851592738,//创建时间
    "roleName": "王二测",//角色名称
    "roleStatus": "ENABLE",//角色状态 ENABLE 启用 DISABLE 禁用
    "roleRemark": null,//角色备注
    "admin": false // 是否为管理员  true 是  false 否
  }
}
```

##### 3.2 创建角色

接口说明: 不能创建管理员角色，管理员角色由系统自动生成，系统最多只能有一个管理员角色

接口地址：/rbac/role/add

请求方式：POST

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| roleName| String | 角色名称 （长度最小1最大4）|

返回结果：
```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 3.3 编辑角色

接口地址：/rbac/role/update

请求方式：POST

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| roleId| String | 角色ID|
| roleName| String | 角色名称 （长度最小1最大4）|
| roleRemark| String | 角色备注|

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 3.4 禁用角色

接口说明: 管理员不能被禁用

接口地址：/rbac/role/disable

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| roleId| String | 角色ID|

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 3.5 启用角色

接口说明: 管理员不能被禁用

接口地址：/rbac/role/enable

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| roleId| String | 角色ID|

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 3.6 删除角色

接口说明: 管理员不能被删除，角色下没有关联用户的时候可以删除角色，同时删除角色授权的菜单关联信息

接口地址：/rbac/role/remove

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| roleId| String | 角色ID|

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```

##### 3.7.1 获取角色权限信息

接口说明: 管理员不用授权

接口地址：/rbac/role/loadMenus

请求方式：GET

请求参数：

| 请求参数 | 参数类型   | 参数说明 |
|------|--------|------|
| roleId| String | 角色ID|

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": [
        {
            "id": "0db02ad647fc4c4b971412021d880210", //id
            "resourceId": "rbac:role:edit", //资源id
            "resourceType": "ACTION", //资源类型
            "authCode": "rbac:role:delete" //权限字符
        },
        {
            "id": "9e2d8a09d3754b119fa90e1a18b58985",
            "resourceId": "rbac:role:edit",
            "resourceType": "ACTION",
            "authCode": "rbac:role:enable"
        }
    ]
}
```


##### 3.7 角色授权

接口说明: 给角色授权，包括菜单与功能权限；管理员不用授权

接口地址：/rbac/role/grantMenus

请求方式：POST

请求参数：

| 请求参数        | 参数类型   | 参数说明 |
|-------------|--------|------|
| roleId      | String | 角色ID|
| resourceIds | String[] | 资源ID数组 （单个传值 rbac:role:edit 多个传值用逗号分隔 rbac:role:edit,rbac:role:create）|

返回结果：

```json
{
 "code": 0, //编码
 "success": true, //请求成功标志
 "msg": null, //消息
 "data": null //数据
}
```
