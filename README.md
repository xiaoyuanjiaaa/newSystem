## 平台简介


## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 缓存监控：对系统的缓存信息查询，命令统计等。
17. 在线构建器：拖动表单元素生成相应的HTML代码。
18. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 模块功能
1.  admin：业务存放地址。
2.  common：通用模块。
3.  framework：未使用。
4.  generator：代码生成工具。
5.  quartz：定时器管理，未使用。
6.  system：系统javabean存放。

## swagger访问地址
http://127.0.0.1:8080/swagger-ui/index.html

## 生产服务器
1. 向日葵远程连接：经常变动，找五院对接人
2. jar包存放位置： /opt/yqfk/ruoyi-admin.jar
3. 后台日志存放地址：/home/ruoyi/logs/
4. pc前端发布位置：/usr/local/nginx/html/yqfk
5. 小程序前端发布位置：/usr/local/nginx/html/wechat
6. redis/fdfs/mysql：地址都是本机。配置修改 ruoyi-admin/src/main/resources/application-prod.yml


## sql
由于生产mysql设置成大小写敏感，qrtz相关的表名需要改成大写

## 测试库登录账号
admin/admin123

