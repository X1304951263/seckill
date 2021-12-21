// pages/goodInfo/goodInfo.js
Page({

    data: {
        info: '',
        disabled: true,
        codename: '抢购',
        time: ''
    },

    onLoad: function (options) {
        console.log(options.id)
        wx.request({
            url: 'http://localhost:8080/goodInfo', 
            method: 'POST',
            data: {
              id: options.id,
            },
            header: {
              'content-type': 'application/x-www-form-urlencoded', // 默认值
              'token': wx.getStorageSync('token')
            },
            success:res=> {
              console.log(res.data)
              if(res.data.code == 200){
                var just = new Date();
                var a = res.data.data.startTime;
                //console.log(a)
                //var newString = a.slice(0,22) + '-0800';
                // var c = new Date(newString).toISOString();
                var end = new Date(a);
                var t = a.slice(0,19);
                res.data.data.startTime = t;
                var d = (end - just) / 1000;
                var tt = Math.round(d);
                console.log(tt)
                this.setData({
                    info: res.data.data,
                })
                var num = tt;
        var that = this;
        that.setData({
            codename: "倒计时：" + num + " S"
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
                codename: "倒计时：" + num + " S",
                disabled: true
              })
            }
          }, 1000)
        }
            }
          })
    },

    buy(){
      var num = 3;
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
      wx.request({
        url: 'http://localhost:8080/buy', 
        method: 'POST',
        data: {
          goodId: this.data.info.id
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded', // 默认值
          'token': wx.getStorageSync('token')
        },
        success:res=> {
          if(res.data.code == 200){
            console.log('抢购成功')
            wx.showLoading({
              title: '查询订单中...',
            })
            var id = this.data.info.id
            var timer1 = setInterval(function () {
              console.log(id)
              wx.request({
                url: 'http://localhost:8080/orderInfo', 
                method: 'POST',
                data: {
                  goodId: id
                },
                header: {
                  'content-type': 'application/x-www-form-urlencoded', // 默认值
                  'token': wx.getStorageSync('token')
                },
                success:res=> {
                  console.log(res.data)
                  if(res.data.code == 200){
                    clearInterval(timer1);
                    wx.hideLoading({
                      success: (res) => {},
                    })
                    wx.navigateTo({
                      url: '../order/order?id=' + id,
                    })
                  }
                },
              })
            },3000)
          }else if(res.data.code == 300){
            console.log('抢购失败')
            console.log(res.data)
            clearInterval(timer);
            this.setData({
              disabled: true,
              codename: '活动已结束',
            })
          }
        }
      })
    },
    onShow: function () {
  
    },

})