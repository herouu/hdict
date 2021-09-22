
sample
===
* 注释

	select #{use("cols")} from en_words  where  #{use("condition")}

cols
===
	word,translation

updateSample
===
	
	word=#{word},translation=#{translation}

condition
===

	1 = 1  
	-- @if(!isEmpty(word)){
	 and word=#{word}
	-- @}
	-- @if(!isEmpty(translation)){
	 and translation=#{translation}
	-- @}
	
	