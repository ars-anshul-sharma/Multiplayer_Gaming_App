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
    data: JSON.stringify({ amount: amount }),
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
