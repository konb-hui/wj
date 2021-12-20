<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu ref="sideMenu" @handchange="listByType"></SideMenu>
    </el-aside>
    <el-main>
      <books class="books-area"  ref="booksArea"> </books>
    </el-main>
  </el-container>
</template>

<script>
  import book from "@/api/library/book"
  import SideMenu from './LibraryLeft'
  import Books from './Books'
  export default {
    name: 'library',
    components: {SideMenu, Books},
    data() {
        return {

        }
    },
    created() {
    },
    mounted() {
    },
    methods: {
        listByType(tid) {
            book.list(tid, 1, this.$refs.booksArea.size, '')
            .then((response) => {
                this.$refs.booksArea.books = response.data.books.records
                this.$refs.booksArea.current = response.data.books.current
                this.$refs.booksArea.size = response.data.books.size
                this.$refs.booksArea.total = response.data.books.total
                this.$refs.booksArea.tid = tid
            })
        }
    },
  }
</script>

<style scoped>
  .books-area {
    width: 990px;
    margin-left: auto;
    margin-right: auto;
  }
</style>