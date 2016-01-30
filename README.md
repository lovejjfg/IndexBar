# IndexMasterDemo
this is about  index..
![index](https://raw.githubusercontent.com/lovejjfg/IndexMasterDemo/master/index.gif)
* 可以自己设置背景的状态
* 可自定义textsize
* 可自定义选中文字的颜色

       <com.lovejjfg.indexdemo.IndexBar
              android:id="@+id/index_bar"
              android:layout_width="30dp"
              app:normalColor="#ffff"
              app:selecColor="#00f"
              app:indexSize="14sp"
              android:layout_height="match_parent"
              android:layout_alignParentRight="true"
              android:background="@drawable/index_bar_bg"/>
          
* 相关的接口回调

      indexBar.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
                  @Override
                  public void onLetterChange(String letter) {
      //                ToastUtil.showToast(getApplicationContext(), letter);
                      showTextView(letter);
                      for(int i=0; i<mPersons.size(); i++) {
                          String firstPinyin = String.valueOf(mPersons.get(i).getPinyin().charAt(0));
                          if(TextUtils.equals(letter, firstPinyin)) {
                              mLv.setSelection(i);
                              break;
                          }
                      }
                  }
              });
