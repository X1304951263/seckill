// pages/order/order.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        info: '',
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        console.log(options.id)
        wx.request({
            url: 'http://localhost:8080/orderInfo', 
            method: 'POST',
            data: {
              goodId: options.id
            },
            header: {
              'content-type': 'application/x-www-form-urlencoded', // 默认值
              'token': wx.getStorageSync('token')
            },
            success:res=> {
              if(res.data.code == 200){
                this.setData({
                    info: res.data.data
                })
              }
            }
          })
    },

    
})