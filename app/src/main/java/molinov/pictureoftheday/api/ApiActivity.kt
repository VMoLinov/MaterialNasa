package molinov.pictureoftheday.api

//private const val EARTH = 0
//private const val MARS = 1
//private const val WEATHER = 2
//
//class ApiActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityApiBinding
//
//    @SuppressLint("InflateParams")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityApiBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.apply {
//            viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
//            indicator.setViewPager(viewPager)
//            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                tab.text = when (position) {
//                    EARTH -> "EARTH"
//                    MARS -> "MARS"
//                    WEATHER -> "SYSTEM"
//                    else -> "EARTH"
//                }
//            }.attach()
//            tabLayout.apply {
//                getTabAt(EARTH)?.setIcon(R.drawable.ic_earth)
//                getTabAt(MARS)?.setIcon(R.drawable.ic_mars)
//                getTabAt(WEATHER)?.setIcon(R.drawable.ic_system)
//            }
//            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    when (position) {
//                        EARTH -> EarthFragment()
//                        MARS -> MarsFragment()
//                        WEATHER -> WeatherFragment()
//                        else -> EarthFragment()
//                    }
//                }
//            })
//        }
//    }
//}
