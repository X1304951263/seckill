// pages/sale/sale.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        disabled: true,
        codename: '抢购'
    },
    btn(){
        console.log('1111');
    },
    onShow: function () {
        var num = 1006;
        var that = this;
        that.setData({
            codename: num + "S"
        })
        var timer = setInterval(function () {
            num--;
            if (num <= 0) {
              clearInterval(timer);
              that.setData({
                codename: '抢购',
                disabled: false
              })
            } else {
              that.setData({
                codename: num + "S",
                disabled: true
              })
            }
        }, 1000)
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