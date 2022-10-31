$(function(){
    $('#myForm').submit(function(e){
        e.preventDefault()  //prevents unnecessary refresh of page
        $(this).validate() 
        
        if ($(this).valid()) {
            data = getFormData($("#myForm"))  // assignning form data to variable called data
            alert(data);
            data = { "data": data, "action": "register" } //name of data and action of page
            $.post('Model/user_register.py', data, function(dt)
             {
                 if(dt)
                 {
                     alert("Data Inserted Sucessfully");
                     console.log(dt);
                //location.reload();
                 }
            })
        }
    }) 

    
})