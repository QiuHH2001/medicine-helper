<template>
  <div class="app-container">
  <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="姓名"  />
      <el-table-column prop="name" label="药品" />
      <!-- <el-table-column label="图片">
        <template slot-scope="scope">
            <img :src="scope.row.picture" alt=""  height="90">
        </template>
      </el-table-column> -->
      <el-table-column prop="dose" label="剂量"  />
      <el-table-column prop="administration" label="服用方式"  />
      <el-table-column prop="indication" label="症状"  />
      <el-table-column prop="time" label="服用时间"  />
    </el-table>
  </div>
</template>

<script>
import * as doctor from '@/api/doctor/doctor'
import Cookies from 'js-cookie'
export default {
  data() {
    return {
      list:[],
      doctorId: Cookies.get('doctorId')
    }
  },
  created(){
    this.getAllHistory()
  },
  methods: {
    getAllHistory(){
      doctor.getAllHistory(this.doctorId, "1").then(response => {
        this.list = response.data
      }).catch(error => {
        console.log(error)
      })
    }
  },
}
</script>

<style>

</style>
