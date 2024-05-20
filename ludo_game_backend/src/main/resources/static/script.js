//first request- to server to create order
const paymentStart = () => {
  console.log("Payment Started...");
  let amount = $("#payment_field").val();
  console.log(amount);
  if (amount == "" || amount == null) {
    alert("Amount is required!!");
    return;
  }

  $.ajax({
    url: "http://localhost:8090/payment/create-order",
    data: JSON.stringify({
      amount: amount,
      phoneNumber: "+919816321205",
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      console.log(response);
      if (response.status == "created") {
        let options = {
          key: "rzp_test_VEca5dqOUnQdVj",
          amount: response.amount,
          currency: "INR",
          name: "Game Recharge",
          description: "Add money to play games",
          image: "",
          order_id: response.id,
          handler: function (response) {
            console.log(response.razorpay_payment_id);
            console.log(response.razorpay_order_id);
            console.log(response.razorpay_signature);

            updatePaymentOnServer(
              response.razorpay_payment_id,
              response.razorpay_order_id,
              "paid"
            );
          },
          prefill: {
            name: "",
            email: "",
            contact: "",
          },
          notes: {
            address: "Please Games",
          },
          theme: {
            color: "#3399cc",
          },
        };
        let rzp = new Razorpay(options);
        rzp.on("payment.failed", function (response) {
          console.log(response.error.code);
          console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);
          updatePaymentOnServer(
            response.razorpay_payment_id,
            response.razorpay_order_id,
            "failed"
          );
        });
        rzp.open();
      }
    },
    error: function (error) {
      console.log(error);
      alert("Something went wrong!!");
    },
  });
};

function updatePaymentOnServer(payment_id, order_id, status) {
  $.ajax({
    url: "http://localhost:8090/payment/update-order",
    data: JSON.stringify({
      payment_id: payment_id,
      order_id: order_id,
      status: status,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      Swal.fire({
        title: "Good job!",
        text: "Payment Successful!",
        icon: "success",
      });
    },
    error: function (error) {
      Swal.fire({
        title: "Failure!",
        text: "Oops Payment Failed!",
        icon: "error",
      });
    },
  });
}

function updateUserName(phone_number, username) {
  $.ajax({
    url: "http://localhost:8090/user/update",
    data: JSON.stringify({
      phoneNumber: phone_number,
      userName: username,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      Swal.fire({
        title: "Good job!",
        text: response["message"],
        icon: "success",
      });
    },
    error: function (error) {
      Swal.fire({
        title: "Failure!",
        text: error.responseJSON["message"],
        icon: "error",
      });
    },
  });
}

function getUserName(phone_number) {
  $.ajax({
    url: "http://localhost:8090/user/get",
    data: JSON.stringify({
      phoneNumber: phone_number,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      console.log(response);
    },
  });
}

function sendOtp(phoneNumber) {
  $.ajax({
    url: "http://localhost:8090/otp/send-otp",
    data: JSON.stringify({
      phoneNumber: phoneNumber,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      if (response.status == "DELIVERED") {
        Swal.fire({
          title: "Otp Sent!",
          icon: "success",
        });
      } else {
        Swal.fire({
          title: "Failure!",
          text: response.message,
          icon: "error",
        });
      }
    },
  });
}

function validateOtp(phoneNumber, otpNumber, userType) {
  $.ajax({
    url: "http://localhost:8090/otp/validate-otp",
    data: JSON.stringify({
      phoneNumber: phoneNumber,
      otpNumber: otpNumber,
      userType: userType,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      if (response.status == "SUCCESS") {
        Swal.fire({
          title: "Done!",
          text: response.message,
          icon: "success",
        });
      } else {
        Swal.fire({
          title: "Failure!",
          text: response.message,
          icon: "error",
        });
      }
    },
  });
}

function getBalance(phoneNumber) {
  $.ajax({
    url: "http://localhost:8090/user/get-balance",
    data: JSON.stringify({
      phoneNumber: phoneNumber,
    }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (balance) {
      console.log(balance);
    },
  });
}
