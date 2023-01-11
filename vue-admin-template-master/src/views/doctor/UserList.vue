<template>
  <div class="app-container">
    <!--查询表单-->
    <!-- <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="user.name" placeholder="讲师名" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form> -->

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="头像">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" alt="" height="90" />
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="姓名" />
      <el-table-column prop="phone" label="手机" />
      <el-table-column prop="email" label="邮箱" />

      <el-table-column label="过敏药物" align="center">
        <template slot-scope="scope">
          <el-popover placement="right" width="500" trigger="click">
            <el-table :data="allergy">
              <!-- <el-table-column label="图片">
                <template slot-scope="scope">
                  <img :src="scope.row.picture" alt="" height="90" />
                </template>
              </el-table-column> -->
              <el-table-column property="name" label="药品"></el-table-column>
              <el-table-column
                property="property"
                label="性状"
              ></el-table-column>
              <el-table-column
                property="indication"
                label="适用症"
              ></el-table-column>
            </el-table>
            <el-button
              type="primary"
              size="medium"
              icon="el-icon-document"
              @click="getAllergy(scope.row.id)"
              slot="reference"
              >查看</el-button
            >
          </el-popover>

          <el-button
            type="primary"
            size="medium"
            icon="el-icon-edit"
            @click="addAllergy(scope.row.id)"
            >添加</el-button
          >

          <el-dialog title="添加过敏药物" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="药品名称" >
                <el-input style="width: 600px" v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="适用症" >
                <el-input style="width: 600px" v-model="form.indication" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="性状" >
                <el-input style="width: 600px" v-model="form.property" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="saveAllergy"
                >确 定</el-button
              >
            </div>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import * as doctor from "../../api/doctor/doctor";
import Cookies from "js-cookie"
export default {
  data() {
    return {
      list: [],
      // user: {},
      allergy: [],
      dialogFormVisible: false,
      form:{},
      userId: 0,
      doctorId: Cookies.get('doctorId')
    };
  },
  created() {
    console.log("方法未执行");
    this.getAllUser();
    console.log("方法已执行");
    console.log("doctorId", this.doctorId)
  },
  methods: {
    getAllUser() {
      doctor
        .getAllUser(this.doctorId)
        .then((response) => {
          console.log(response.data);
          this.list = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getAllergy(userId) {
      doctor
        .getAllergy(userId)
        .then((response) => {
          this.allergy = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    addAllergy(userId){
      this.dialogFormVisible = true
      this.userId = userId
    },
    saveAllergy(){
      console.log(this.userId, this.form.name, this.form.indication, this.form.property)
      this.dialogFormVisible = false
      doctor.addAllergy(this.userId, this.form.name, this.form.indication, this.form.property)
      .then(response => {
        this.$message({
          message: '添加成功',
          type: 'success'
        });
      }).catch(error => {
        console.log(error)
      })
    }
  },
};
</script>

<style>
</style>
