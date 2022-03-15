package com.jjang051.util;

public class PageWriter {
	//매개변수 total은 전체 게시물수 
	//listPerPage는 한페이지에 보여질 게시물 수
	//pageBlock은 하단에 page에 보여질 블럭 갯수 만약 3이면 <  1/2/3  >   5면   < 1/2/3/4/5 >
	//현재 눌려진 페이지
	//requsetURL은 링크 주소
	public  static String pageWrite(int total,int listPerPage, int pageBlock, int clickPage, String requsetURL) {
		String pageString = "";
		//System.out.println(requsetURL);
		String url = requsetURL;
		//System.out.println("url==="+url);
		if(requsetURL.contains("search_word")) {
			//BoardList.do?search_select="+search_select+"&search_word="+search_word
			url=requsetURL+"&clickPage";
		} else {
			url=requsetURL+"?clickPage";
			//System.out.println("url2==="+url);
		}
		//total을 받아서 전체 페이지 갯수를 구하고 
		int totalPages =  (int)Math.ceil( (double) total / listPerPage  );

		int tempPage =  ((clickPage - 1) / pageBlock)*pageBlock+1;
		//System.out.println("tempPage==="+tempPage);
		if(tempPage!=1) {
			pageString+="<li>"+
					"<a href='"+url+"=1'>"+
						"<span class='material-icons'>keyboard_double_arrow_left</span>"+
					"</a>"+
					"</li>";
			pageString+="<li>"+
						"<a href='"+url+"="+(tempPage-1)+"'>"+
							"<span class='material-icons'>chevron_left</span>"+
						"</a>"+
						"</li>";
		}
		int pageBlockCount = 1;
		while(pageBlockCount<=pageBlock && tempPage<=totalPages) {
			if(tempPage==clickPage) {
				pageString+="<li class='active'><a href=''>"+tempPage+"</a></li>";
			} else {
				pageString+="<li><a href='"+url+"="+tempPage+"'>"+tempPage+"</a></li>";
			}
			tempPage++;
			pageBlockCount++;
		}
		if(tempPage<=totalPages) {
			pageString+="<li>"+
							"<a href='"+url+"="+tempPage+"'>"+
								"<span class='material-icons'>chevron_right</span>"+
							"</a>"+
						"</li>";
			pageString+="<li>"+
					"<a href='"+url+"="+totalPages+"'>"+
						"<span class='material-icons'>keyboard_double_arrow_right</span>"+
					"</a>"+
					"</li>";
		}
		//System.out.println(pageString);
		/*
		<c:if test="${startPage!=1 }">
		<li><a href="BoardList.do?clickPage=${startPage-pageBlock }&search_select=${param.search_select}&search_word=${param.search_word}"><span class="material-icons">chevron_left</span></a></li>
		</c:if>
		<c:forEach var="page" begin="${startPage }" end="${endPage }" step="1" varStatus="loop">
			<li class="${clickPage==page ? 'active':'' }">
			<a href="BoardList.do?clickPage=${page }&search_select=${param.search_select}&search_word=${param.search_word}">${page }</a>
			</li>
		</c:forEach>
		<c:if test="${endPage!=lastPage }">
			<li>
			<a href="BoardList.do?clickPage=${startPage+pageBlock }&search_select=${param.search_select}&search_word=${param.search_word}"><span class="material-icons">chevron_right</span></a>
			</li>
		</c:if>
		*/
		return pageString;
	}
}
