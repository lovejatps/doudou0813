var ioc = {
	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		fields : {
			driverClass : "com.mysql.jdbc.Driver",
			jdbcUrl : "jdbc:mysql://192.168.102.6:3306/doudou?useUnicode=true&characterEncoding=utf-8",
//			jdbcUrl : "jdbc:mysql://127.0.0.1:3306/doudou?useUnicode=true&characterEncoding=utf-8",
			username : "root",
			password : "123456",
			validationQuery : "SELECT 'x'",
//			driverClass : "oracle.jdbc.driver.OracleDriver",
//			jdbcUrl : "jdbc:oracle:thin:@127.0.0.1:1521:orcl",
//			username : "doudou",
//			password : "ok",
//			validationQuery : "SELECT 'x' from dual",
			testWhileIdle : true,
			testOnBorrow : false,
			testOnReturn : false
		}
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [ {
			refer : 'dataSource'
		} ]
	},
	
	loginFilter : {
		type : "com.uniits.doudou.filter.LoginFilter"
	}
	
	/*
	filePool : {
		type : "net.wendal.nutz.ext.WebFilePool",
		args : ["upload/", 2000]
	},
	uploadCtx : {
		type : "org.nutz.mvc.upload.UploadingContext",
		args : [{refer : "filePool"}],
		fields : {
			maxFileSize : 10240000,
			nameFilter : ".+(jpg|gif|png)$"
		}
	},
	upload : {
		type : "org.nutz.mvc.upload.UploadAdaptor",
		args : [{refer : "uploadCtx"}]
	}
	*/
};