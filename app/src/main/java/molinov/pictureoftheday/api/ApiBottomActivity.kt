package molinov.pictureoftheday.api

//class ApiBottomActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityApiBottomBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityApiBottomBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.bottom_view_earth -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.activity_api_bottom_container, ViewPagerItems())
//                        .commit()
//                    true
//                }
//                R.id.bottom_view_mars -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.activity_api_bottom_container, MarsFragment())
//                        .commit()
//                    true
//                }
//                R.id.bottom_view_weather -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.activity_api_bottom_container, WeatherFragment())
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }
//        binding.bottomNavigationView.apply {
//            selectedItemId = R.id.bottom_view_earth
//            getOrCreateBadge(R.id.bottom_view_earth)
//            val badge = getBadge(R.id.bottom_view_earth)
//            badge?.number = 3
//        }
//    }
//}
