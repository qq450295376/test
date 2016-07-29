package cn.ucai.fulicenter.utils;

public interface I {

	 interface User {
		String TABLE_NAME							=		"t_superwechat_user";
		String USER_NAME 							= 		"m_user_name";					//鐢ㄦ埛璐﹀彿
		String PASSWORD 							= 		"m_user_password";				//鐢ㄦ埛瀵嗙爜
		String NICK 								= 		"m_user_nick";					//鐢ㄦ埛鏄电О
	}
	
	 interface Contact {
		String TABLE_NAME 							= 		"t_superwechat_contact";
		String CONTACT_ID 							= 		"m_contact_id";					//涓婚敭
		String USER_NAME 							= 		"m_contact_user_name";			//鐢ㄦ埛璐﹀彿
		String CU_NAME 								= 		"m_contact_cname";				//濂藉弸璐﹀彿
	}
	
	 interface Group {
		String TABLE_NAME 							= 		"t_superwechat_group";
		String GROUP_ID 							= 		"m_group_id";					// 涓婚敭
		String HX_ID 								= 		"m_group_hxid";					//鐜俊缇ょ粍id
		String NAME 								= 		"m_group_name";					//缇ょ粍鍚嶇О
		String DESCRIPTION 							= 		"m_group_description";			//缇ょ粍绠�浠�
		String OWNER 								= 		"m_group_owner";				//缇ょ粍鎵�鏈夎�咃紞鐢ㄦ埛璐﹀彿
		String MODIFIED_TIME 						= 		"m_group_last_modified_time";	//鏈�鍚庝慨鏀规椂闂�
		String MAX_USERS 							= 		"m_group_max_users";			//鏈�澶т汉鏁�
		String AFFILIATIONS_COUNT 					= 		"m_group_affiliations_count";	//缇ょ粍浜烘暟
		String IS_PUBLIC 							= 		"m_group_is_public";			//缇ょ粍鏄惁鍏紑
		String ALLOW_INVITES 						= 		"m_group_allow_invites";		//鏄惁鍙互閭�璇�
	}
	
	 interface Member {
		String TABLE_NAME 							= 		"t_superwechat_member";
		String MEMBER_ID 							= 		"m_member_id";					//涓婚敭
		String USER_NAME 							= 		"m_member_user_name";			//鐢ㄦ埛璐﹀彿
		String GROUP_ID 							= 		"m_member_group_id";			//缇ょ粍id
		String GROUP_HX_ID 							= 		"m_member_group_hxid";			//缇ょ粍鐜俊id
		String PERMISSION 							= 		"m_member_permission";			//鐢ㄦ埛瀵圭兢缁勭殑鏉冮檺\n0:鏅�氱敤鎴穃n1:缇ょ粍鎵�鏈夎��
	}
	
	 interface Avatar {
		String TABLE_NAME 							= 		"t_superwechat_avatar";
		String AVATAR_ID 							= 		"m_avatar_id";					//涓婚敭
		String USER_NAME 							= 		"m_avatar_user_name";			//鐢ㄦ埛璐﹀彿鎴栬�呯兢缁勮处鍙�
		String AVATAR_PATH 							= 		"m_avatar_path";				//淇濆瓨璺緞
		String AVATAR_TYPE 							= 		"m_avatar_type";				//澶村儚绫诲瀷锛歕n0:鐢ㄦ埛澶村儚\n1:缇ょ粍澶村儚
		String UPDATE_TIME 							= 		"m_avatar_last_update_time";	//鏈�鍚庢洿鏂版椂闂�
	}
	
	 interface Location {
		String TABLE_NAME 							= 		"t_superwechat_location";
		String LOCATION_ID 							= 		"m_location_id";				//涓婚敭
		String USER_NAME 							= 		"m_location_user_name";			//鐢ㄦ埛璐﹀彿
		String LATITUDE 							= 		"m_location_latitude";			//绾害
		String LONGITUDE 							= 		"m_location_longitude";			//缁忓害
		String IS_SEARCHED 							= 		"m_location_is_searched";		//鏄惁鍙互琚悳绱㈠埌
		String UPDATE_TIME 							= 		"m_location_last_update_time";	//鏈�鍚庢洿鏂版椂闂�
	}
	String SERVER_ROOT								=		"http://10.0.2.2:8888/SuperWeChatServer/Server";
//	String AVATAR_PATH 								= 		"E:/test/";
	String ISON8859_1 								= 		"iso8859-1";
	String UTF_8 									= 		"utf-8";
	String PAGE_ID 									= 		"page_id";						//鍒嗛〉鐨勮捣濮嬩笅鏍�
	String PAGE_SIZE 								= 		"page_size";					//鍒嗛〉鐨勬瘡椤垫暟閲�
	int DEFAULT_DISTANCE = 100; // 鏌ユ壘闄勮繎鐨勪汉鏃讹紝榛樿璺濈涓�100KM
	int ID_DEFAULT									=		0;								//ID榛樿鍊�
	int UN_READ_MSG_COUNT_DEFAULT					=		0;								//鏈娑堟伅鏁伴噺榛樿鍊�
	int GROUP_MAX_USERS_DEFAULT 					= 		-1;								//缇ょ粍鏈�澶т汉鏁伴粯璁ゅ��
	int GROUP_AFFILIATIONS_COUNT_DEFAULT 			= 		1;								//缇ょ粍浜烘暟榛樿鍊�
	int PERMISSION_NORMAL							= 		0;								//鏅�氱敤鎴风兢缁勬潈闄�
	int PERMISSION_OWNER							= 		1;								//缇ょ粍鎵�鏈夎�呯兢缁勬潈闄�
	int AVATAR_TYPE_USER							=		0;								//鐢ㄦ埛澶村儚
	int AVATAR_TYPE_GROUP							=		1;								//缇ょ粍澶村儚
	int GROUP_PUBLIC								=		1;								//鍏紑缇ょ粍
	int GROUP_NO_PUBLIC								=		0;								//闈炲叕寮�缇ょ粍
	String BACKSLASH								= 		"/";							//鍙嶆枩鏉�
	String AVATAR_TYPE_USER_PATH					= 		"user_avatar";					//鐢ㄦ埛澶村儚淇濆瓨鐩綍
	String AVATAR_TYPE_GROUP_PATH 					=		"group_icon";					//缇ょ粍澶村儚淇濆瓨鐩綍
	String AVATAR_SUFFIX_PNG						=		".png";							//PNG鍥剧墖鍚庣紑鍚�
	String AVATAR_SUFFIX_JPG						=		".jpg";							//JPG鍥剧墖鍚庣紑鍚�
	String QUESTION									=		"?";							//问号
	String EQU										=		"=";							//等号
	String MSG_PREFIX_MSG							=		"msg_";							//消息吗前缀
	String AND										=		"&";							//&符号
	int LOCATION_IS_SEARCH_ALLOW					=		1;								//鍙互琚悳绱㈠埌鍦扮悊浣嶇疆
	int LOCATION_IS_SEARCH_INHIBIT					=		0;								//绂佹琚悳绱㈠埌鍦扮悊浣嶇疆
	int MSG_SUCCESS						            =  		0;							    //榛樿鎴愬姛
	int MSG_REGISTER_USERNAME_EXISTS				=		101;							//璐﹀彿宸茬粡瀛樺湪
	int MSG_REGISTER_FAIL							=		102;							//娉ㄥ唽澶辫触
	int MSG_UNREGISTER_FAIL							=		103;							//瑙ｉ櫎娉ㄥ唽澶辫触
	int MSG_USER_SEARCH_FAIL		    			=		104;							// 鏌ユ壘鐢ㄦ埛澶辫触
	int MSG_LOGIN_UNKNOW_USER						=		105;							//璐︽埛涓嶅瓨鍦�
	int MSG_LOGIN_ERROR_PASSWORD					=		106;							//璐︽埛瀵嗙爜閿欒
	int MSG_LOGIN_SUCCESS							=		107;							//鐧婚檰鎴愬姛
	int MSG_USER_SAME_NICK							=		108;							//鏄电О鏈慨鏀�
	int MSG_USER_UPDATE_NICK_FAIL					=		109;							//鏄电О淇敼澶辫触
	int MSG_USER_SAME_PASSWORD						=		110;							//鏄电О鏈慨鏀�
	int MSG_USER_UPDATE_PASSWORD_FAIL				=		111;							//鏄电О淇敼澶辫触
	int MSG_LOCATION_UPLOAD_FAIL					=		112;							//鐢ㄦ埛涓婁紶鍦扮悊浣嶇疆澶辫触
	int MSG_LOCATION_UPDATE_FAIL					=		113;							//鐢ㄦ埛鏇存柊鍦扮悊浣嶇疆澶辫触
	int MSG_REGISTER_UPLOAD_AVATAR_FAIL				=		201;							//涓婁紶澶村儚澶辫触
	int MSG_UPLOAD_AVATAR_FAIL						=		202;							//鏇存柊澶村儚澶辫触
	int MSG_CONTACT_FIRENDED						=		301;							//宸茬粡鏄ソ鍙嬪叧绯�
	int MSG_CONTACT_ADD_FAIL						=		302;							//濂藉弸鍏崇郴娣诲姞澶辫触
	int MSG_CONTACT_DEL_FAIL						=		303;							//濂藉弸鍏崇郴鍒犻櫎澶辫触
	int MSG_GET_CONTACT_ALL_FAIL					=		304;							// 鑾峰彇鍏ㄩ儴濂藉弸鍒楄〃澶辫触
	int MSG_GET_CONTACT_PAGES_FAIL					=		305;							// 鍒嗛〉鑾峰彇濂藉弸鍒楄〃澶辫触
	int MSG_GROUP_HXID_EXISTS						=		401;							//缇ょ粍鐜俊ID宸茬粡瀛樺湪
	int MSG_GROUP_CREATE_FAIL						=		402;							//鍒涘缓缇ょ粍澶辫触
	int MSG_GROUP_ADD_MEMBER_FAIL					=		403;							//娣诲姞缇ょ粍鎴愬憳澶辫触
	int MSG_GROUP_GET_MEMBERS_FAIL					=		404;							//鑾峰彇缇ゆ垚鍛樺け璐�
	int MSG_GROUP_UNKONW							=		405;							//缇ょ粍涓嶅瓨鍦�
	int MSG_GROUP_SAME_NAME							=		406;							//鏈夌浉鍚岀兢缁勫悕绉�
	int MSG_GROUP_UPDATE_NAME_FAIL					=		407;							//缇ょ粍鍚嶇О淇敼澶辫触
	int MSG_GROUP_DELETE_MEMBER_FAIL				=		408;							//鍒犻櫎缇ょ粍鎴愬憳澶辫触
	int MSG_GROUP_DELETE_MEMBERS_FAIL				=		409;							//鍒犻櫎澶氱兢缁勬垚鍛樺け璐�
	int MSG_GROUP_DELETE_FAIL						=		410;							//鍒犻櫎缇ょ粍澶辫触
	int MSG_GROUP_FIND_BY_GOURP_ID_FAIL				=		411;							//鏍规嵁缇ょ粍id鏌ユ壘缇ょ粍澶辫触
	int MSG_GROUP_FIND_BY_HX_ID_FAIL				=		412;							//鏍规嵁鐜俊id鏌ユ壘缇ょ粍澶辫触
	int MSG_GROUP_FIND_BY_USER_NAME_FAIL			=		413;							//鏌ユ壘鐢ㄦ埛鍚嶇О鏌ユ壘鏌ユ壘缇ょ粍澶辫触
	int MSG_GROUP_FIND_BY_GROUP_NAME_FAIL			=		414;							//鏌ユ壘缇ょ粍鍚嶇О鏌ユ壘鏌ユ壘缇ょ粍澶辫触
	int MSG_PUBLIC_GROUP_FAIL						=		415;							//鏌ユ壘鍏紑缇ゅけ璐�
	int MSG_LOCATION_GET_FAIL						=		501;							// 鑾峰彇闄勮繎鐨勪汉澶辫触
	int MSG_UNKNOW									=		999;							//鏈煡閿欒
	int MSG_ILLEGAL_REQUEST							=		-1;							//闈炴硶璇锋眰

	String KEY_REQUEST 								= 		"request";
	/** 涓婁紶鍥剧墖鐨勭被鍨嬶細user_avatar鎴杇roup_icon */
	String AVATAR_TYPE 								= 		"avatarType";
	/** 鐢ㄦ埛濮撳悕鎴杊xid */
	String NAME_OR_HXID                             =       "name_or_hxid";
	/** 鏈嶅姟鍣ㄧ姸鎬佺殑璇锋眰 */
	String REQUEST_SERVERSTATUS 					= 		"server_status";
	/** 瀹㈡埛绔彂閫佺殑娉ㄥ唽璇锋眰 */
	String REQUEST_REGISTER		 					= 		"register";
	/**  鍙戦�佸彇娑堟敞鍐岀殑璇锋眰 */
	String REQUEST_UNREGISTER 						= 		"unregister";
	/** 瀹㈡埛绔笂浼犲ご鍍忕殑璇锋眰 */
	String REQUEST_UPLOAD_AVATAR 					= 		"upload_avatar";
	/** 瀹㈡埛绔洿鏂扮敤鎴锋樀绉扮殑璇锋眰 */
	String REQUEST_UPDATE_USER_NICK 				= 		"update_nick";
	/** 瀹㈡埛绔慨鏀瑰瘑鐮佺殑璇锋眰 */
	String REQUEST_UPDATE_USER_PASSWORD 			= 		"update_password";
	/** 瀹㈡埛绔彂閫佺殑鐧婚檰璇锋眰 */
	String REQUEST_LOGIN 							= 		"login";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇鐢ㄦ埛澶村儚璇锋眰 */
	String REQUEST_DOWNLOAD_AVATAR	 				= 		"download_avatar";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇鑱旂郴浜烘墍鏈夐泦鍚堣姹� */
	String REQUEST_DOWNLOAD_CONTACT_ALL_LIST 		= 		"download_contact_all_list";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇鑱旂郴浜洪泦鍚堣姹� */
	String REQUEST_DOWNLOAD_CONTACT_PAGE_LIST 		= 		"download_contact_page_list";
	/** 瀹㈡埛绔彂閫佺殑鍒犻櫎鑱旂郴浜鸿姹� */
	String REQUEST_DELETE_CONTACT 					= 		"delete_contact";
	/** 瀹㈡埛绔彂閫佺殑娣诲姞鑱旂郴浜鸿姹� */
	String REQUEST_ADD_CONTACT 						= 		"add_contact";
	/** 瀹㈡埛绔彂閫佺殑鏌ユ壘鐢ㄦ埛璇锋眰 */
	String REQUEST_FIND_USER 						= 		"find_user";
	/** 瀹㈡埛绔彂閫佺殑鏍规嵁鐢ㄦ埛鎴栨樀绉版ā绯婃煡鎵剧敤鎴疯姹� */
	String REQUEST_FIND_USERS_FOR_SEARCH			= 		"find_users_for_search";
	/** 瀹㈡埛绔彂閫佺殑涓婁紶浣嶇疆璇锋眰 */
	String REQUEST_UPLOAD_LOCATION 					= 		"upload_location";
	/** 瀹㈡埛绔彂閫佺殑鏇存柊浣嶇疆璇锋眰 */
	String REQUEST_UPDATE_LOCATION 					= 		"update_location";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇浣嶇疆璇锋眰 */
	String REQUEST_DOWNLOAD_LOCATION 				= 		"download_location";
	/** 瀹㈡埛绔彂閫佺殑鍒涘缓缇ょ粍璇锋眰 */
	String REQUEST_CREATE_GROUP			 			= 		"create_group";
	/** 瀹㈡埛绔彂閫佺殑娣诲姞缇ゆ垚鍛樿姹� */
	String REQUEST_ADD_GROUP_MEMBER 				= 		"add_group_member";
	/** 瀹㈡埛绔彂閫佺殑娣诲姞澶氫釜缇ゆ垚鍛樿姹� */
	String REQUEST_ADD_GROUP_MEMBERS		 		= 		"add_group_members";
	/** 瀹㈡埛绔彂閫佺殑鏇存柊缇ゅ悕绉拌姹� */
	String REQUEST_UPDATE_GROUP_NAME 				= 		"update_group_name";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇澶氫釜缇ゆ垚鍛樿姹� */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS 			= 		"download_group_members_by_groupid";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇澶氫釜缇ゆ垚鍛樿姹� */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_LIMIT 	= 		"download_group_members_by_limit";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇澶氫釜缇ゆ垚鍛樿姹� */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID 	= 		"download_group_members_by_hxid";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇澶氫釜缇ゆ垚鍛樿姹� */
	String REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID_LIMIT 	= 		"download_group_members_by_hxid_limit";
	/** 瀹㈡埛绔彂閫佺殑鍒犻櫎缇ゆ垚鍛樿姹� */
	String REQUEST_DELETE_GROUP_MEMBER 				= 		"delete_group_member";
	/** 瀹㈡埛绔彂閫佺殑鍒犻櫎澶氫釜缇ゆ垚鍛樿姹� */
	String REQUEST_DELETE_GROUP_MEMBERS 			= 		"delete_group_members";
	/** 瀹㈡埛绔彂閫佺殑鍒犻櫎缇ょ粍璇锋眰 */
	String REQUEST_DELETE_GROUP 					= 		"delete_group";
	/** 瀹㈡埛绔彂閫佺殑涓嬭浇鍏紑瑁欒姹� */
	String REQUEST_FIND_PUBLIC_GROUPS 				= 		"download_public_groups";
	/** 瀹㈡埛绔彂閫佺殑鏍规嵁缇ょ粍鍚嶇О妯＄硦鏌ユ壘缇ょ粍璇锋眰 */
	String REQUEST_FIND_GROUP_BY_GROUP_NAME 		= 		"find_group_by_group_name";
	/** 瀹㈡埛绔彂閫佺殑鐢ㄦ埛濮撳悕鏌ユ壘鐢ㄦ埛鎵�鍦ㄧ殑缇ょ粍璇锋眰 */
	String REQUEST_FIND_GROUP_BY_USER_NAME 			= 		"find_group_by_user_name";
	/** 瀹㈡埛绔彂閫佺殑鏍规嵁缇ょ粍璐﹀彿鏌ユ壘缇ょ粍璇锋眰 */
	String REQUEST_FIND_GROUP_BY_ID					= 		"find_group_by_group_id";
	/** 瀹㈡埛绔彂閫佺殑鏍规嵁缇ょ粍鐜俊id鏌ユ壘缇ょ粍璇锋眰 */
	String REQUEST_FIND_GROUP_BY_HXID 				= 		"find_group_by_group_hxid";
	/** 瀹㈡埛绔彂閫佺殑鏍规嵁缇ょ粍鐜俊id鏌ユ壘鍏紑缇ょ粍璇锋眰 */
	String REQUEST_FIND_PUBLIC_GROUP_BY_HXID 		= 		"find_public_group_by_group_hxid";
}
