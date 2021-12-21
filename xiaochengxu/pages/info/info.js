// pages/info/info.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        gender: 0,
        city: 0,
        hiddenmodalput: true,
        hiddenmodalput1: true,
        nickName: '璀璨的独孤',
        newName: '璀璨的独孤',
        signature: '一曲红尘烟雨梦，此生何惧君芳华!',
        newSignature: '一曲红尘烟雨梦，此生何惧君芳华!',
    },

    chooseSex() {
        wx.showActionSheet({
          itemList: ['男','女'],
          success: res=>{
            if(!res.tapIndex == this.data.gender ){
                this.setData({
                    gender: res.tapIndex
                }),
                wx.request({
                  url: 'url',
                })
            }
          },
        })
    },

    setCity() {
        wx.showActionSheet({
          itemList: ['小营','清河','沙河','健翔桥'],
          success: res=>{
            this.setData({
                city: res.tapIndex
            }),
            wx.request({
                url: 'url',
            })
          },
        })
    },

    setNickName(){
        this.setData({
            hiddenmodalput: false,
        })
    },
    setSignature(){
        this.setData({
            hiddenmodalput1: false,
        })
    },
    getInputValue(e){
        this.setData({
            newName: e.detail.value
        })
    },
    getInputValue1(e){
        this.setData({
            newSignature: e.detail.value
        })
    },
    confirm(){
        this.setData({
            hiddenmodalput: true,
            nickName: this.data.newName,
        })
    },
    confirm1(){
        this.setData({
            hiddenmodalput1: true,
            signature: this.data.newSignature,
        })
    },

    cancel(){
        this.setData({
            hiddenmodalput: true,
        })
    },
    cancel1(){
        this.setData({
            hiddenmodalput1: true,
        })
    },











    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})