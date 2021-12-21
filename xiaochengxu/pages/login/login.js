// pages/login/login.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        id: '',
        password: ''
    },

    getInputValue(e) {
        this.setData({
            id: e.detail.value
        })
    },

    getInputValue1(e) {
        this.setData({
            password: e.detail.value
        })
    },

    login(){
        wx.request({
            url: 'http://localhost:8080/login', 
            method: 'POST',
            data: {
              id: this.data.id,
              password: this.data.password
            },
            header: {
              'content-type': 'application/x-www-form-urlencoded' // 默认值
            },
            success (res) {
              console.log(res.data)
              if(res.data.code == 200){
                wx.setStorageSync('token', res.data.msg)
                wx.setStorageSync('user', res.data.data)
                wx.navigateTo({
                  url: '../list/list',
                })
              }
            }
        })
    }


})