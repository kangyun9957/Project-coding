from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
import collections
import time

b=4

Url=['http://prod.danawa.com/list/?cate=112747','http://prod.danawa.com/list/?cate=112751',
     'http://prod.danawa.com/list/?cate=112752','http://prod.danawa.com/list/?cate=112753','http://prod.danawa.com/list/?cate=112760'
     ,'http://prod.danawa.com/list/?cate=112763','http://prod.danawa.com/list/?cate=112772',]
driver = webdriver.Chrome(ChromeDriverManager().install())
def Pagelist():     # 해당 웹의 페이지수를 불러옴
    num=[]
    page = driver.find_element_by_class_name("number_wrap")
    page_number=page.find_elements_by_class_name("num")
    
    for pages in page_number:
        num.append(int(pages.text))
    print(num)
    return num
def PageSkip():     #스킵버튼클릭
     
     pages = driver.find_element_by_class_name("num_nav_wrap")
     try:
         pages_number=pages.find_element_by_class_name("nav_next")
     except:
         return None
     skip=pages_number.text
     print(skip)

     return skip
def Pageparser():  #웹데이타 긁기
    proinfortuple = collections.namedtuple("날짜",['지역','기후','시간'])
    proinfobox=[]
    proinfo=[]
    product_layer = driver.find_element_by_class_name("main_prodlist_list")
    product_list = product_layer.find_elements_by_class_name("prod_item")
    for product_li in product_list:
        proinfobox.append(product_li)
    
    for product_div in proinfobox:
        product_name = product_div.find_element_by_class_name("prod_name")
        product_spec = product_div.find_element_by_class_name("spec_list")
        product_pricebox = product_div.find_element_by_class_name("price_sect")
        product_price = product_pricebox.find_element_by_tag_name('a')
        proinfo.append(proinfortuple(product_name.text,product_spec.text,product_price.text))
    
        
     		
    print(proinfo)
  




    
def PageClick(a,b):  #웹 클릭이벤트 작용
    page_event=driver.find_element_by_xpath(f'//*[@id="productListArea"]/div[{b}]/div/div/a[{a}]')
    
    try:
        page_event.send_keys('\n')
    except:
        print("why")
    
    time.sleep(2)
def SkipClick():     #스킵 클릭이벤트 작용
    skip_event=driver.find_element_by_class_name("nav_next")
    try:
        skip_event.send_keys('\n')
    except:
        print("why")
    
    time.sleep(2)
    


#만약에 다음으로 넘어가는 버튼이있으면 다음으로 넘어가서 다시 로직을 돌리고 없을시에 그다음 URL로 넘어간다
for j in range (0,len(Url)):
    print(Url[j])
    driver.get(Url[j])
    skipchecker=0

    while(PageSkip() !=None):
        
        Pagelist()
        Pageparser()
        PageClick(2,5)
        for i in range(1,len(Pagelist())-1):
            if(Pagelist()[i] != None):
                PageClick(Pagelist()[i]-(skipchecker*10),b)
                Pageparser()
            else:
                break
        SkipClick()
        skipchecker+=1
    

        
        



