
        $form=$("form[name='search']");
		$page=$form.find("input[name='page']");

		//페이지 번호 클릭시 이동. 근데 검색조건과 함께 페이징 되야 함
		$('ul.pagination li a[data-page]').click(function(e) {
			e.preventDefault();
			$page.val($(this).data("page"));
			$form.submit();
		}); // ul.pagination li a[data-page]


		//검색버튼
		$form.find("button[type=submit]").click(function(e) {
			e.preventDefault();
			$page.val(1);
			$form.submit();
		});



		// 목록 갯수 변경
		$('#id_size').change(function(e) {
			$page.val(1);
			$form.find("input[name='size']").val($(this).val());
			$form.submit();
		}); // '#id_rowSizePerPage'.change

		// 초기화 버튼 클릭
		$('#id_btn_reset').click(function() {
			$page.val(1);
			$form.find("input[name='searchWord']").val("");
			$form.find("select[name='searchType'] option:eq(0)").attr("selected", "selected");
			$form.find("select[name='searchCategory'] option:eq(0)").attr("selected", "selected");
		}); // #id_btn_reset.click



		$("#excelDown").click( function(e){
			var $form=$("form[name='search']");
			$(this).attr("href","<c:url value='/free/excelDown' />"+"?"+$form.serialize() );
		}); //excelDown click