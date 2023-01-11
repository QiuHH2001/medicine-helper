### 封装代码

```js
import axios from 'axios';
export default class Request {
  static async fetch(url, jsonData = {}, method = 'get', responseType) {
    let baseUrl;
    const host = window.location.origin || 'http://localhost';
    let config = {
      baseURL: baseUrl,
      method: method,
      timeout: 15000
    };
    config = Object.assign(config, responseType); //设置返回的数据类型
    if (method === 'get') {
      config = {params: jsonData, ...config};
    } else {
      config = {data: jsonData, ...config}
    }
    return axios(url, config, )
      .then(response => {
        return response;
      })
      .catch(error => {
        return {
          status: 500,
          statusText: error.message || "network error",
          data: {status: 500, msg: error.message || "network error"},
        }
      });
  }
}
```

### 全局挂载

```
Vue.prototype.$request = Request;
```

### 使用案例

- post

  ```js
  let res = await this.$request.fetch("/api/classify/updateCategory",{ _id,name },"post");
  ```

- get

  ```js
  let res = await this.$request.fetch("/api/classify/getCategoryAccount"); //默认get可以不传
  ```

- put、delete同post

### 错误捕获

```JSON
例如拿到的结果如下：

{
  msg: "获取成功"
	result: [
  	{
  		_id: "60bd812f1f82c93c1059b91c", 
  		name: "最新公告：今天高考加油！",
  		__v: 0
		}
	]
	status: 0
}

这样后期有问题修改起来也简单
if (res.status === 0) {
  this.data = res.result;
} else {
  // 弹窗
}
```

