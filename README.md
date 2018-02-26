##一 博客项目简介：
通过业余时间自学Java写成的博客系统，技术栈是Spring,Spring MVC,JPA,MYSQL,bootstrap,Ajax,Thymeleaf。

##二 博客系统安装：
1.	安装MySQL 5.7+，并启动,然后g新建数据库tmyblog。
2.	安装Elasticsearch 2.4.4，并启动。

##三 博客使用步骤：
1. 起动后，首先访问http://localhost:8081/register,进入注册页面注册。
![](D:/Tmyblog/src/main/resources/static/img/register.png '注册页面')。
2. 注册之后，你可以使用账号登录，输入账号密码就可以登录，如果忘记密码可以点击·忘记密码·，进行密码重置。
如果你想保存密码，可以点击·记住我·，把账号密码保存到cookie中，避免再次登录时输入账号密码。
![](D:/Tmyblog/src/main/resources/static/img/login.png '登录页面')
3. 进入用户博客列表后，在左侧可以看到你的所有文章，右侧上方可以看到个人头像，用户名，邮箱和简介，左侧下方可以看到文章归档。
![](D:/Tmyblog/src/main/resources/static/img/list.png '列表页面')。
你还可以点击导航栏·博客首页·查看博客系统所有文章。进入博客具体页面可以添加评论。
4. 点击·博客管理·对博客进行增删查改
![](D:/Tmyblog/src/main/resources/static/img/create.png '新增博客页面')。
5. 点击·用户管理·对用户进行更新操作，可以更换头像，用户名，邮箱，添加个人简介。
![](D:/Tmyblog/src/main/resources/static/img/admin.png '更新页面')。

##四 未完待续
1. 博客正文的图片插入。
2. 个人私信和系统广播。
3。 使用Ajax实现前后端交互。


