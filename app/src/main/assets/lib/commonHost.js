//J2V8 Host interact with accessors and Module
// created by vimal moorthy krishnamoorthy, 2017

//defining input,output,intialize,fire functions

var input=function(inputName,type)
{
alert( "Input Name:"+inputName+" type is:"+type);
};
var output=function(outputName,type)
{
alert( "output Name:"+outputName+" type is:"+type);
};
var display=function(name)
{

alert(name);
}



define(function(require, exports, module) {
  console.log("Function : J2V8Host");
   var inputarr="";
   var outputarr="";
   var inputName;
   var inputtype;
   var outputName;
   var outputtype;


//defining input,ouput,inputList,outputList
    exports.input=function(inputName,type) {
        this.inputName=inputName;
        inputarr+=inputName+":" +type;
};
 exports.output= function(inputName1,type1) {
        outputarr+=inputName+":" +type;

   };


 exports.inputList= function() {
       return inputarr;

      };

  exports.outputList= function(inputName1,type1) {
       return outputarr;

         };

exports.display=function(name){
alert(name);
return name;
};
});






