<template>
  <div class="app-container product-add">
    <el-form ref="templateForm" :model="formData" :inline="false" :rules="formRules" label-position="left" label-width="100px" @submit.native.prevent @validate="ClearYclCheck()">
      <div class="input-container">
        <el-form-item style="margin-left: 0px !important">
          <el-button @click="$router.go(-1)">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </el-form-item>
        <el-row>
          <el-col :span="8">
            <el-form-item label="模板编码" prop="template_code">
              <el-input v-model="formData.template_code" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="模板名称" prop="template_name">
              <el-input v-model="formData.template_name" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="XXXX" prop="template_XXXX">
              <el-input v-model="formData.template_XXXX" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="XXXX" prop="template_select_XXXX">
              <el-select id="furnace_select" v-model="formData.template_select_XXXX" placeholder="请输入" style="width:180px" filterable>
                <el-option
                  v-for="item in furnaceArr"
                  :key="item.seq_id"
                  :label="item.furnace_name"
                  :value="item.seq_id"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="templateData">
          <el-table :data="formData.templateData" style="width:80%;margin-bottom:10px;margin-left:0px" stripe border>
            <el-table-column :render-header="renderHeader" prop="seq_id" style = "width:5%">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click.native.prevent="delData(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
            <el-table-column label="问题编码" prop="question_code" style = "width:5%" />
            <el-table-column label="问题标题" prop="question_title" style = "width:10%" />
            <el-table-column label="问题文言" prop="question_des" style = "width:15%" />
            <el-table-column label="问题顺序" prop="question_order" style = "width:5%" />
            <el-table-column label="图像" style = "width:8%">
              <template slot-scope="scope">
                <img :src= "'/src/'+ scope.row.question_img" min-width="50" height="50">
              </template>
            </el-table-column>
            <el-table-column label="新规担当" prop="create_user" style = "width:10%" />
            <el-table-column label="新规时间" prop="create_time" style = "width:12%" />
            <el-table-column label="变更担当" prop="update_user" style = "width:10%" />
            <el-table-column label="变更时间" prop="update_time" style = "width:12%" />
            <el-table-column label="操作" fixed="right" style = "width:5%">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="handleEdit(scope.$index,scope.row)"
                >编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </div>
    </el-form>
    <el-dialog :append-to-body="true" :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="questionShow" title="问题编辑" width="1000px" >
      <el-form ref="questionForm" :model="questionData" :rules="questionRules" label-position="left" label-width="100px" @submit.native.prevent >
        <el-form-item style="margin-left: 0px !important">
          <el-button @click="questionShow = false;index = -1">取消</el-button>
          <el-button type="primary" @click="addQuestion">保存</el-button>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="问题编码" prop="question_code">
              <el-input v-model="questionData.question_code" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="问题标题" prop="question_title">
              <el-input v-model="questionData.question_title" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="问题文言" prop="question_des">
              <el-input v-model="questionData.question_des" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="问题顺序" prop="question_order">
              <el-input v-model="questionData.question_order" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="问题类型" prop="question_type">
              <el-select id="furnace_select" v-model="questionData.question_type" placeholder="请输入" style="width:180px" filterable>
                <el-option
                  v-for="item in furnaceArr"
                  :key="item.seq_id"
                  :label="item.furnace_name"
                  :value="item.seq_id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="示例图片" prop="question_img">
              <el-button id = "btnS" size="small" @click="uploadP">图片上传</el-button>
              <el-upload
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :limit="3"
                :action="url"
                :on-exceed="handleExceed"
                :file-list="fileList"
                style="display:none"
              >
                <el-button id = "btnH" size="small">图片上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="XXXX" prop="question_XXXX">
              <el-input v-model="questionData.question_XXXX" placeholder="请输入" maxlength="10" style="width:180px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="XXXX" prop="question_select_XXX">
              <el-select id="furnace_select" v-model="questionData.question_select_XXX" placeholder="请输入" style="width:180px" filterable>
                <el-option
                  v-for="item in furnaceArr"
                  :key="item.seq_id"
                  :label="item.furnace_name"
                  :value="item.seq_id"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { upload } from '@/api/template'
const nowDate = new Date()
const Y = nowDate.getFullYear()
let mon = nowDate.getMonth() + 1
let d = nowDate.getDate()
let H = nowDate.getHours()
let i = nowDate.getMinutes()
let s = nowDate.getSeconds()
if (mon < 10) {
  mon = '0' + mon
}
if (d < 10) {
  d = '0' + d
}
if (H < 10) {
  H = '0' + H
}
if (i < 10) {
  i = '0' + i
}
if (s < 10) {
  s = '0' + s
}
const nowDateTime = Y + '-' + mon + '-' + d + ' ' + H + ':' + i + ':' + s
export default {
  data() {
    return {
      url: upload,
      questionShow: false,
      fdisable: false,
      fcheckNum: 0,
      formData: {
        template_code: '',
        template_name: '',
        template_XXXX: '',
        template_select_XXXX: '',
        furnace_id: '',
        templateData: []
      },
      formRules: {},
      action: '',
      furnaceArr: [
        {
          seq_id: '1',
          furnace_name: 'XXXX'
        },
        {
          seq_id: '2',
          furnace_name: 'XXXX'
        },
        {
          seq_id: '3',
          furnace_name: 'XXXX'
        }
      ],
      questionData: {
        question_code: '',
        question_title: '',
        question_des: '',
        question_order: 0,
        question_type: '',
        create_time: nowDateTime,
        question_img: '',
        create_user: 'user',
        update_time: nowDateTime,
        update_user: 'user',
        question_XXXX: '',
        question_select_XXX: ''
      },
      questionRules: {},
      index: -1,
      fileList: []
    }
  },
  created() {
    if (this.$route.params.id != null) {
      this.action = 'edit'
      this.formData.template_code = 'Q001'
      this.formData.template_name = '问题1'
      this.formData.template_XXXX = 'XXXX'
      this.formData.furnace_id = '1'
      this.formData.template_select_XXXX = '2'
      this.formData.templateData = [
        {
          question_code: 'Q001',
          question_title: '你最喜欢的品牌是什么？',
          question_des: '你为什么最喜欢',
          question_order: 1,
          create_time: '2019-08-01 16:32:45',
          question_img: 'img/Kaola.jpg',
          create_user: 'user',
          update_time: '2019-08-01 17:36:04',
          update_user: 'user'
        }, {
          question_code: 'Q002',
          question_title: '你最喜欢的包是什么？',
          question_des: '你为什么最喜欢',
          question_order: 2,
          create_time: '2019-08-01 16:32:45',
          question_img: 'img/Kaola.jpg',
          create_user: 'user',
          update_time: '2019-08-01 17:36:04',
          update_user: 'user'
        }, {
          question_code: 'Q003',
          question_title: '你最喜欢的香水是什么？',
          question_des: '你为什么最喜欢',
          question_order: 3,
          create_time: '2019-08-01 16:32:45',
          question_img: 'img/Kaola.jpg',
          create_user: 'user',
          update_time: '2019-08-01 17:36:04',
          update_user: 'user'
        }
      ]
    } else {
      // 新增的场合
      this.action = 'create'
    }
  },
  methods: {
    ClearYclCheck() {
      if (!!window.ActiveXObject || 'ActiveXObject' in window) {
        if (this.fcheckNum < 2) {
          this.$refs['templateForm'].clearValidate()
          this.fcheckNum++
        }
      }
    },
    renderHeader(h) {
      return h('el-button', {
        on: {
          click: () => {
            this.questionShow = true
            this.index = -1
          }
        },
        props: {
          type: 'text',
          icon: 'el-icon-plus'
        }
      }, '追加')
    },
    delData(key) {
      this.formData.templateData.splice(key, 1)
    },
    save() {
      this.$refs.templateForm.validate(valid => {
        if (valid) {
          if (this.action === 'create') {
            alert('新增操作')
          } else {
            alert('编辑操作')
          }
        }
      })
    },
    handleEdit(ind, row) {
      this.questionShow = true
      this.index = ind
      this.questionData.question_code = row.question_code
      this.questionData.question_title = row.question_title
      this.questionData.question_des = row.question_des
      this.questionData.question_order = row.question_order
      this.questionData.question_type = row.question_type
      this.questionData.question_img = row.question_img
      this.questionData.update_time = nowDateTime
      this.questionData.update_user = 'user'
      this.questionData.create_time = nowDateTime
      this.questionData.create_user = 'user'
      this.questionData.question_XXXX = row.question_XXXX
      this.questionData.question_select_XXX = row.question_select_XXX
      console.log(row)
    },
    addQuestion() {
      if (this.index != -1) {
        // this.formData.templateData[this.index] = this.questionData
        this.formData.templateData.splice(this.index, 1, this.questionData)
      } else {
        this.questionData.update_time = nowDateTime
        this.questionData.update_user = 'user'
        this.questionData.create_time = nowDateTime
        this.questionData.create_user = 'user'
        this.formData.templateData.push(this.questionData)
      }
      this.questionShow = false
      this.index = -1
      this.questionData = {}
      console.log(this.formData.templateData)
    //   this.resetForm('questionForm')
    },
    uploadP() {
      var btn = document.getElementById('btnH')
      btn.click()
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}
</script>
<style>
.el-form-item__content{
    margin-left: 0px !important
}
</style>
