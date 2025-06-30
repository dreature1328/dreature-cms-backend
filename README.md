# 基于 Spring Cloud 的微服务博客系统

本项目是一个采用微服务架构的博客系统，基于 Spring Cloud，提供用户支持、内容浏览、社区互动、后台管理等功能。

## 核心架构与技术栈

> 后端基于 Spring Boot 开发，利用 Spring Cloud Netflix 套件实现服务治理、配置管理、负载均衡与网关路由。系统功能通过多个独立的微服务实现，并使用 Nginx + Zuul 进行反向代理与负载均衡。数据存储结合 MySQL 进行持久化、Redis 缓存热点数据。ORM 框架选用 MyBatis。

**开发与数据处理**

- **基础框架：** Spring Boot
- **数据访问：** MyBatis

**微服务框架**

- **分布式系统核心构建**：Spring Cloud Netflix
- **服务注册与发现：** Eureka
- **API 网关：** Zuul
- **客户端负载均衡：** Ribbon（集成于 Zuul/Feign）
- **集中配置管理：** Spring Cloud Config

**基础设施/中间件**

- **反向代理与负载均衡：** Nginx（前端入口） + Zuul（网关层）
- **数据持久化：** MySQL（主数据存储）
- **缓存：** Redis（热点数据缓存，提升性能）

## 微服务划分

核心业务微服务

- 用户服务（User Service）：登录注册等
- 内容服务（Content Service）：文章呈现、按类筛选、热度排行等
- 交互服务（Interaction Service）：点赞收藏等
- 评论服务（Comment Service）：评论等
- 媒体服务（Media Service）：图片上传等

基础设施与中间件服务

- 网关服务（Gateway Service）：路由转发、负载均衡等
- 注册服务（Registry Service）：微服务实例注册等
- 配置服务（Config Service）：集中配置等
