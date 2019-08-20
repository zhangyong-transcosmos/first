<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="filters" :inline="true" :model="filters" @submit.native.prevent>
        <el-form-item>
          <el-button type="primary" @click="doAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="result-container">
      <el-table
        v-loading="listLoading"
        :data="listData"
        :header-cell-style="{background:'#ecf5ff'}"
        stripe
        element-loading-text="Loading"
        border
        @selection-change="handleSelectionChange"
      >
        <el-table-column :selectable="handleSelectable" type="selection" fixed="left"/>
        <el-table-column label="No" type="index" prop="seq_id" width="50"/>
        <el-table-column label="模板编码" prop="plan_no" width="120">
          <template slot-scope="scope">{{ scope.row.plan_no }}</template>
        </el-table-column>
        <el-table-column label="模板名称" prop="sap_order_no" width="120">
          <template slot-scope="scope">{{ scope.row.sap_order_no }}</template>
        </el-table-column>
        <el-table-column label="XXXX" prop="furnace_name" width="120">
          <template slot-scope="scope">{{ scope.row.furnace_name }}</template>
        </el-table-column>
        <el-table-column label="XXXX">
          <template slot-scope="scope">{{ scope.row.alloy_code }}</template>
        </el-table-column>
        <el-table-column label="XXXX" width="150">
          <template slot-scope="scope">{{ scope.row.alloy_name }}</template>
        </el-table-column>
        <el-table-column label="XXXX">
          <template slot-scope="scope">{{ scope.row.alloy_type }}</template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template slot-scope="scope">{{ scope.row.start_date | DateFormat }}</template>
        </el-table-column>
        <el-table-column label="新规担当" width="100">
          <template slot-scope="scope">{{ scope.row.produce_count }}</template>
        </el-table-column>
        <el-table-column label="新规时刻" width="100">
          <template slot-scope="scope">{{ scope.row.create_time }}</template>
        </el-table-column>
        <el-table-column label="变更担当" width="100">
          <template slot-scope="scope">
            <template v-if="scope.row.short_run_flag == 0">否</template>
            <template v-if="scope.row.short_run_flag == 1">是</template>
          </template>
        </el-table-column>
        <el-table-column label="变更是个">
          <template slot-scope="scope">{{ scope.row.update_time }}</template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="190">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleEdit(scope.row)"
            >编辑</el-button>
            <el-button
              type="text"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <el-pagination
        :current-page="filters.page"
        :page-sizes="[10,20,30,50]"
        :page-size="filters.limit"
        :total="total"
        :layout="total>0?'total, sizes, prev, pager, next, jumper':'total, sizes, prev, pager, next'"

        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      listLoading: true, // 加载中
      filters: {
        page: 1,
        limit: 10,
        furnace_id: [],
        startDate: '',
        endDate: '',
        plan_status: ['0', '1']
      }, // 筛选条件
      total: 6, // 查询结果总条数
      listData: [
        {
          alloy_code: '1000040',
          alloy_id: '0ea14e935e9247a39367a493bc6b618a',
          alloy_name: '10179-020',
          alloy_type: 'M-6093',
          create_time: '2019-08-01 16:32:45',
          create_user: '4e99ba0a65944c8bb60e33e045186bdd',
          current_produce_count: 1,
          delete_flag: 0,
          factory_area_id: '58951cc2c65c4e32bf486b30acadce6d',
          furnace_id: '2c68a12e97ca4f879531981eac03ea7f',
          furnace_name: '12',
          is_make_order: 1,
          memo: '',
          order_count: 0,
          overseas_flag: 0,
          plan_no: '1219080101',
          plan_status: 1,
          produce_count: 3,
          sap_order_no: '',
          seq_id: '1c7527f7a2694901825f15e69b850917',
          short_run_flag: 0,
          start_date: '2019-08-01 16:31:58',
          together_boiler_sequence: 1,
          update_time: '2019-08-01 17:36:04',
          update_user: '4e99ba0a65944c8bb60e33e045186bdd'
        }, {
          alloy_code: '1000040',
          alloy_id: '0ea14e935e9247a39367a493bc6b618a',
          alloy_name: '10179-020',
          alloy_type: 'M-6093',
          create_time: '2019-07-26 14:02:18',
          create_user: '4e99ba0a65944c8bb60e33e045186bdd',
          current_produce_count: 0,
          delete_flag: 0,
          factory_area_id: '58951cc2c65c4e32bf486b30acadce6d',
          furnace_id: 'f987cb17201641ed90f410641889efca',
          furnace_name: '15',
          is_make_order: 1,
          memo: '',
          order_count: 0,
          overseas_flag: 0,
          plan_no: '1519072601',
          plan_status: 1,
          produce_count: 2,
          sap_order_no: '0726-01',
          seq_id: '32dac89d4d7f466aaef68c8df7148b70',
          short_run_flag: 0,
          start_date: '2019-07-26 14:01:58',
          together_boiler_sequence: 1,
          update_time: '2019-07-26 16:56:05',
          update_user: '4e99ba0a65944c8bb60e33e045186bdd'
        },
        {
          alloy_code: '1000056',
          alloy_id: '7982ac55f9234bd393d2919fd531d4ae',
          alloy_name: '10028-020',
          alloy_type: 'M-6097',
          create_time: '2019-07-25 11:43:43',
          create_user: '4e99ba0a65944c8bb60e33e045186bdd',
          current_produce_count: 0,
          delete_flag: 0,
          factory_area_id: '58951cc2c65c4e32bf486b30acadce6d',
          furnace_id: '2c68a12e97ca4f879531981eac03ea7f',
          furnace_name: '12',
          is_make_order: 1,
          memo: '',
          order_count: 2,
          overseas_flag: 0,
          plan_no: '1219072502',
          plan_status: 0,
          produce_count: 12,
          sap_order_no: '072502',
          seq_id: 'da1a01f98b494900b6efb5a6b75e14c1',
          short_run_flag: 0,
          start_date: '2019-07-25 11:43:28',
          together_boiler_id: 'c5c3c80a83bb469cbf88810a18b82427',
          together_boiler_sequence: 2,
          update_time: '2019-07-25 11:43:43',
          update_user: '4e99ba0a65944c8bb60e33e045186bdd'
        }, {
          alloy_code: '1000040',
          alloy_id: '0ea14e935e9247a39367a493bc6b618a',
          alloy_name: '10179-020',
          alloy_type: 'M-6093',
          create_time: '2019-07-25 11:43:43',
          create_user: '4e99ba0a65944c8bb60e33e045186bdd',
          current_produce_count: 0,
          delete_flag: 0,
          factory_area_id: '58951cc2c65c4e32bf486b30acadce6d',
          furnace_id: '2c68a12e97ca4f879531981eac03ea7f',
          furnace_name: '12',
          is_make_order: 1,
          memo: '',
          order_count: 2,
          overseas_flag: 0,
          plan_no: '1219072501',
          plan_status: 0,
          produce_count: 12,
          sap_order_no: '072501',
          seq_id: 'c5c3c80a83bb469cbf88810a18b82427',
          short_run_flag: 0,
          start_date: '2019-07-25 11:43:19',
          together_boiler_id: 'c5c3c80a83bb469cbf88810a18b82427',
          together_boiler_sequence: 1,
          update_time: '2019-07-25 11:43:43',
          update_user: '4e99ba0a65944c8bb60e33e045186bdd'
        }, {
          alloy_code: '1000040',
          alloy_id: '0ea14e935e9247a39367a493bc6b618a',
          alloy_name: '10179-020',
          alloy_type: 'M-6093',
          create_time: '2019-07-25 14:23:59',
          create_user: '4e99ba0a65944c8bb60e33e045186bdd',
          current_produce_count: 0,
          delete_flag: 0,
          factory_area_id: '58951cc2c65c4e32bf486b30acadce6d',
          furnace_id: 'ad0deb78b191426db3af9ddf23309070',
          furnace_name: '九号合金炉',
          is_make_order: 1,
          memo: '',
          order_count: 0,
          overseas_flag: 0,
          plan_no: '0919072501',
          plan_status: 0,
          produce_count: 5,
          sap_order_no: '0725-01',
          seq_id: '5c03387b62b44d2fbccdb04ff549440b',
          short_run_flag: 0,
          start_date: '2019-07-25 00:00:00',
          together_boiler_sequence: 1,
          update_time: '2019-07-25 14:23:59',
          update_user: '4e99ba0a65944c8bb60e33e045186bdd'
        }, {
          alloy_code: '1000040',
          alloy_id: '0ea14e935e9247a39367a493bc6b618a',
          alloy_name: '10179-020',
          alloy_type: 'M-6093',
          create_time: '2019-07-24 16:09:02',
          create_user: '4e99ba0a65944c8bb60e33e045186bdd',
          current_produce_count: 0,
          delete_flag: 0,
          factory_area_id: '58951cc2c65c4e32bf486b30acadce6d',
          furnace_id: 'ad0deb78b191426db3af9ddf23309070',
          furnace_name: '九号合金炉',
          is_make_order: 1,
          memo: '',
          order_count: 0,
          overseas_flag: 0,
          plan_no: '0919072401',
          plan_status: 0,
          produce_count: 5,
          sap_order_no: '',
          seq_id: '565660019995419c8778db197a39cd7c',
          short_run_flag: 0,
          start_date: '2019-07-24 16:08:18',
          together_boiler_sequence: 1,
          update_time: '2019-07-24 16:09:02',
          update_user: '4e99ba0a65944c8bb60e33e045186bdd'
        }
      ]
    }
  },
  created() {
    this.listLoading = false // 加载中
  },
  methods: {
    doAdd() {
      this.$router.push({ name: 'template-create' })
    },
    handleEdit(row) {
      // 编辑
      this.$router.push({
        name: 'template-edit',
        params: { id: row.seq_id }
      })
    },
    handleDelete(row) {
      this.listData.splice(row.index, 1)
    },
    handleSizeChange(val) {
      // 改变每页显示条数
      this.filters.limit = val
    },
    handleCurrentChange(val) {
      // 点击页码事件
      this.filters.page = val
    },
    handleSelectionChange(val) {
      // 多选CheckBox改变选中事件
      this.selectedData = val
    },
    handleSelectable(row, index) {
      return true
    }
  }
}
</script>

