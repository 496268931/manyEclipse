/* Õ∑≤øtab«–ªª */
function setTab(name,cursel,n){
for(var i=0;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  //menu.className=i==cursel?"current":"";
  menu.className=i==cursel?(/(^|\s)current(\s|$)/.test(menu.className)?menu.className:menu.className+" current"):(" "+menu.className+" ").replace(/current/g,"");
  con.style.display=i==cursel?"inline":"none";
}
}