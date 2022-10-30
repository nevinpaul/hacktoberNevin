const nodemailer = require('nodemailer')
const config = require("./config")

const transporter = nodemailer.createTransport({
    service: config.mail.service,
    auth: {
        user: config.mail.user,
        pass: config.mail.pass
    }
})

const sendMail = function (mailOptions) {
    transporter.sendMail(mailOptions, function(error, info){
        if (error) {
          console.log(error)
        } else {
          console.log('Email sent: ' + info.response + ' => ' + mailOptions.to)
        }
      })
}

module.exports = {
    sendMail
}
//sample node mail code
