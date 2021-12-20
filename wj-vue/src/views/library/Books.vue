<template>
  <div>
    <el-row style="height: 840px;">
      <search-bar @onSearch="searchResult" ref="searchBar"></search-bar>
      <el-tooltip effect="dark" placement="right"
                  v-for="item in books"
                  :key="item.id">
        <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.title}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>{{item.author}}</span> /
          <span>{{item.date}}</span> /
          <span>{{item.press}}</span>
        </p>
        <p slot="content" style="width: 300px" class="abstract">{{item.abs}}</p>
        <el-card style="width: 135px;margin-bottom: 20px;height: 233px;float: left;margin-right: 15px" class="book"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="cover" @click="editBook(item)">
            <img :src="item.cover" alt="封面">
          </div>
          <div class="info">
            <div class="title">
              <a href="">{{item.title}}</a>
            </div>
          </div>
          <div class="author">{{item.author}}</div>
        </el-card>    
        
      </el-tooltip>
      <edit-form @onSubmit="getBooks" ref="edit"></edit-form>
    </el-row>
    <el-row>
      <el-pagination
        :current-page="current"
        :page-size="size"
        :total="total"
        style="padding: 30px 0; text-align: center"
        layout="total, prev, pager, next, jumper"
        @current-change="getBooks">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>

import EditForm from './EditForm'
import SearchBar from './SearchBar'

import book from "@/api/library/book"

  export default {
    name: 'Books',
    components: {EditForm, SearchBar},
    data () {
      return {
        key: '',
        current: 1,
        size: 17,
        total: 0,
        tid: '0',
        books: [],
        selectType: ''
      }
    },
    created() {
        this.getBooks()
    },
    methods: {
        getBooks(pageIndex = 1) {
          console.log("..aa", this.key)
            this.current = pageIndex
            book.list(this.tid, this.current, this.size, this.key)
            .then((response) => {
                this.books = response.data.books.records
                this.current = response.data.books.current
                this.size = response.data.books.size
                this.total = response.data.books.total
            })
        },
        editBook(item) {
          this.$refs.edit.dialogFormVisible = true
          this.$refs.edit.form = {
            id: item.id,
            cover: item.cover,
            title: item.title,
            author: item.author,
            date: item.date,
            press: item.press,
            abs: item.abs,
            tid: item.tid
          }
      },
      searchResult() {
        this.key = this.$refs.searchBar.keywords
        this.getBooks()
      }
    },
  }
</script>

<style scoped>
  .cover {
    width: 115px;
    height: 172px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  img {
    width: 115px;
    height: 172px;
    /*margin: 0 auto;*/
  }

  .title {
    font-size: 14px;
    text-align: left;
  }

  .author {
    color: #333;
    width: 102px;
    font-size: 13px;
    margin-bottom: 6px;
    text-align: left;
  }

  .abstract {
    display: block;
    line-height: 17px;
  }

  a {
    text-decoration: none;
  }

  a:link, a:visited, a:focus {
    color: #3377aa;
  }
</style>