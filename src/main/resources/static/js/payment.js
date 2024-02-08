const paymentStart = () => {
    console.log("payment started..");
    let amount=$("#amount").val()
    console.log(amount);
    if(amount=='' || amount==null){
    swal("Failed!", "Amount is required !!", "error");
    return;
    }

    $.ajax({
        url:'/user/create_order',
        data:JSON.stringify({amount:amount, info:'order_request'}),
        contentType:'application/json',
        type:'POST',
        dataType:'json',
        success:function(response){
            console.log(response)
            if(response.status == 'created'){
                let options={
                    key:'rzp_test_2pXfcejfFOKN7P',
                    amount:response.amount,
                    currency:'INR',
                    name:'TechQWare',
                    description:'Services',
                    image:'https://images.crunchbase.com/image/upload/c_pad,h_256,w_256,f_auto,q_auto:eco,dpr_1/qotplnmiwxxvi8anhljv',
                    order_id:response.id,
                    handler:function(response){
                        console.log(response.razorpay_payment_id)
                        console.log(response.razorpay_order_id)
                        console.log(response.razorpay_signature)
                        console.log("payment successful !!")
                        swal("Good job!", "Congrats!! Payment Successful", "success");
                    },
                     prefill: {
                            name: "",
                            email: "",
                            contact: "",
                     },
                     notes: {
                            address: "TechQWare HeadQuarter Noida",
                     },
                     theme: {
                              color: "#012652",
                     }
                };
                let rzp = new Razorpay(options);
                rzp.on('payment.failed', function (response){
                        console.log(response.error.code);
                        console.log(response.error.description);
                        console.log(response.error.source);
                        console.log(response.error.step);
                        console.log(response.error.reason);
                        console.log(response.error.metadata.order_id);
                        console.log(response.error.metadata.payment_id);
                        swal("Failed","Oops!! Payment Failed","error");
                });
                rzp.open()
            }
        },
        error:function(error){
            console.log(error)
            swal("Failed","Something went wrong !!","error");
        }
    })
};