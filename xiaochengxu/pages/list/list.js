// pages/list/list.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        list: [],
    },

    goodInfo(event){
        wx.navigateTo({
            url: '../goodInfo/goodInfo?id=' + event.currentTarget.dataset.id,
        })
    },

    onShow: function () {
        wx.request({
          url: 'http://localhost:8080/goodsList',
          method: 'POST',
          header: {
            'content-type': 'application/x-www-form-urlencoded', // 默认值
            'token': wx.getStorageSync('token')
          },
          success: res => {
            console.log(res.data)
            if(res.data.code == 200){
                this.setData({
                    list: res.data.data
                })
                console.log(this.data.list)
            }
          }
        })
    },
})