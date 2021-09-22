
sample
===
* 注释

	select #{use("cols")} from sys_user  where  #{use("condition")}

cols
===
	id,name,department_id,create_time

updateSample
===
	
	id=#{id},name=#{name},department_id=#{departmentId},create_time=#{createTime}

condition
===

	1 = 1  
	-- @if(!isEmpty(id)){
	 and id=#{id}
	-- @}
	-- @if(!isEmpty(name)){
	 and name=#{name}
	-- @}
	-- @if(!isEmpty(departmentId)){
	 and department_id=#{departmentId}
	-- @}
	-- @if(!isEmpty(createTime)){
	 and create_time=#{createTime}
	-- @}
	
	