package com.misit.deallist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.misit.deallist.fragment.DealListFragment
import com.misit.neardeal.fragment.ProductListFragment

class ProductActivity : AppCompatActivity() {
    private var productListFragment : ProductListFragment? = null
    private var dearListFragment : DealListFragment? = null
    private var mSectionPagerAdapter : SectionPagerAdapter? = null
    private var mViewPager : ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
//        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)

        mSectionPagerAdapter= SectionPagerAdapter(supportFragmentManager)
        mViewPager = findViewById<View>(R.id.container) as ViewPager
        mViewPager?.adapter = mSectionPagerAdapter
        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        mViewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))




    }
    class PlaceholderFragment : Fragment(){
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_product,container,false)
            val textView = rootView.findViewById<View>(R.id.section_label) as TextView
            textView.text = getString(R.string.section_format,arguments!!.getInt(ARG_SECTION_NUMBER))
            return rootView
        }
        companion object{
            private const val ARG_SECTION_NUMBER = "section_number"
            fun  newInstance(sectionNumber : Int):PlaceholderFragment{
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER,sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
    inner class SectionPagerAdapter(fm: FragmentManager? ):FragmentPagerAdapter(fm!!){
        override fun getItem(position: Int):Fragment{
            when(position){
                0 -> return productListFragment!!
                1 -> return dearListFragment!!
            }
            return PlaceholderFragment.newInstance(position+1)
        }
        override fun getCount():Int{
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0->return "Product"
                1->return "Deal"
            }
            return super.getPageTitle(position)
        }
        init {
            val storeId = intent.getStringExtra(KEY_STORE_ID)
            val argument = Bundle()
            argument.putString(KEY_STORE_ID,storeId)
            productListFragment = ProductListFragment()
            productListFragment?.arguments = argument
            dearListFragment = DealListFragment()
            dearListFragment?.arguments=argument
        }
    }
    companion object{
        const val KEY_STORE_ID = "store_id"
    }
}
