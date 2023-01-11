## 目录
- [语法](#语法)
  - [变量声明](#变量声明)
  - [字符串](#字符串)
  - [对象](#对象)
  - [数组](#数组)
  - [解构赋值](#解构赋值)
  - [函数](#函数)
  - [构造函数和类](#构造函数和类)
  - [迭代](#迭代)
- [代码风格](#代码风格)
  - [引号](#引号)
  - [分号](#分号)
  - [缩进](#缩进)
  - [空行](#空行)
  - [链式赋值](#链式赋值)

## 语法
### 变量声明

**1. 声明变量禁止使用 `var` 关键字，应默认使用 `const`，如确认该变量有修改的操作再将关键字修改为 `let`。**

```js
// bad
var a = 1;

// good
const a = 1;

// good
let a = 1;
if (a < 10) {
  count += 1;
}
```

**2. 不确定变量的结果需提前声明变量时，预先指定变量的类型。**

```js
// bad
let a;
let b;
let obj;

// good
let a = '';
let b = 0;
let obj = null;

if(true){
  a = 'sun';
  b = 6;
  obj = {
    name: 'sun';
  }
}
```

### 字符串
**1. 拼接字符串时使用模板字符串语法。**

```js
const a = 'bel';
// bad
const b = a + 'star';

// good
const b = `${a}star`;
```

### 对象
**1. 使用字面量方式创建 Object 类型变量，而不是 `new` 构造函数。**

```js
// bad
const obj = new Object();

// good
const obj = {};

// best
const obj = Object.create(null);
```

**2. 一般的属性名不使用 `''`，只对特殊的属性名使用`''`。**

```js
// bad
const obj = {
  'name': 'sun',
  'age': 18,
  'company-address': 'Beijing'
}

// good
const obj = {
  name: 'sun',
  age: 18,
  'company-address': 'Beijing'
}
```

**3. 对象属性采用简写方式。**

```js
// bad
const obj = {
  name: name
}

// good
const obj = {
  name
}
```

**4. 将简写的对象属性放在对象的开始位置**

```js
// bad
const obj = {
  age: 18,
  name,
  tel: '18888888888',
  email
}

// good
const obj = {
  name,
  email
  age: 18,
  tel: '18888888888'
}
```

**5. 使用 `.` 访问对象的属性。**

```js
const obj = {
  name: 'sun'
}

// bad
const name = obj['name'];

// good
const name = obj.name;
```

### 数组
**1. 使用字面量方式创建 Array 类型变量，而不是 `new` 构造函数。**

```js
// bad
const arr = new Array();

// good
const arr = [];
```

**2. 使用 `...` 扩展运算符复制数组。**

```js
const arr1 = [1, 2, 3];
// bad
const arr2 = [];
for (let i = 0;i < arr1.length;i++){
  arr2[i] = arr1[i];
}

// good
const arr2 = [...arr1];
```

**3. 向数组中添加元素时，使用 `push` 方法。**

```js
const arr = [];

// bad
arr[arr.length] = 'sun';

// good
arr.push('sun');
```

### 解构赋值
**1. 访问和使用对象的多个属性时，使用对象的解构赋值。**

```js
// bad
function getFullName (user) {
  const firstName = user.firstName;
  const lastName = user.lastName;

  return `${firstName} ${lastName}`;
}

// good
function getFullName (user) {
  const { firstName, lastName } = user;

  return `${firstName} ${lastName}`;
}

// best
function getFullName ({ firstName, lastName }) {
  return `${firstName} ${lastName}`;
}
```

**2. 导入模块时同样使用解构赋值导入。**

```js
// bad
import Util from './util';
const util1 = Util.util1;
const util2 = Util.util2;

// good
import { util1, util2 } from './util';
```

### 函数
**1. 使用函数声明，而不是函数表达式。**

```js
// bad
const foo = function () {
  // do something
}

// good
function foo() {
  // do something
}
```

**2. 不直接使用 `arguments`，应通过 `...` 转换为数组后再使用。**

```js
// bad
function foo() {
  const args = Array.prototype.slice.call(arguments);
  return args.join('');
}

// good
function foo(...args) {
  return args.join('');
}
```

**3. 不更改函数参数的值，而是使用默认参数。**

```js
// bad
function foo(opts) {
  opts = opts || {};
}

// good
function foo(opts = {}) {
  // ...
}
```

**4. 当要使用匿名函数时，使用箭头函数，尤其是回调函数。**

```js
// bad
[1, 2, 3].map(function (item) {
  const a = item + 1;
  return item * a;
})

// good
[1, 2, 3].map(item => {
  const a = item + 1;
  return item * a;
})
```

### 构造函数和类
**1. 始终使用 `class`，避免直接操作 `prototype`。**

```js
// bad
function Queue(contents = []) {
  this.queue = [...contents];
}
Queue.prototype.pop = function () {
  const value = this.queue[0];
  this.queue.splice(0, 1);
  return value;
};

// good
class Queue {
  constructor(contents = []) {
    this.queue = [...contents];
  }
  pop() {
    const value = this.queue[0];
    this.queue.splice(0, 1);
    return value;
  }
}
```

**2. 类名始终大写。**

```js
// bad
class math {
  // ...
}

// good
class Math {
  // ...
}
```

### 迭代
**1. 不声明多余的 `iterators`。**

```js
const numbers = [1, 2, 3, 4, 5];

// bad
let sum = 0;
for (let i = 0; i < numbers.length; i++) {
  sum += num;
}

// good
let sum = 0;
numbers.forEach((num) => (sum += num));

// better
const sum = numbers.reduce((total, num) => total + num, 0);
```

## 代码风格
良好的代码风格能大大提高代码可读性，有助于提升 Code Review 的效率，但往往不会影响代码的功能，以下为本规范针对几个争议较多的代码风格进行团队的整体约束和建议。

### 引号
**1. JS 中的字符串统一使用单引号的形式 `''`。**

```js
// bad
const a = "sun";

// good
const a = 'sun';
```
### 分号
**1. 遵循 Airbnb 的规范，推荐行末加分号。**

> JS 中关于要不要加分号的讨论很多，但一般在格式化插件和 Webpack 的作用后都会统一，为提高代码易读性，本规范建议加分号。

```js
// bad
const a = 1

// good
const a = 1;
```

### 缩进
**1. 为保持统一的代码风格，约定使用 `两个空格` 进行缩进，而不是 `Tab`。**

> 可在 VSCode 编辑器主界面下方配置缩进方式。

```js
// bad
function foo() {
    const a = 1;
}

// good
function foo() {
  const a = 1;
}
```

### 空行
**1. 为不影响代码可读性，约定最大连续空行数为 2。**

### 链式赋值
**1. 约定禁止使用链式赋值。**

```js
// bad
const a = b = c = 1;

// good
const a = 1;
const b = 1;
const c = 1;
```