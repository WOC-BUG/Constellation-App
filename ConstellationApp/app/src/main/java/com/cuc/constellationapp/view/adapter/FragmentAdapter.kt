
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(private val fragmentManager: FragmentManager, private val fragmentList: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    //设置每一个的内容
    override fun getItem(arg0: Int): Fragment {
        return fragmentList[arg0] //你要显示的东西显示在这里面就可以了 (Fragment)
    }

    //设置有多少内容
    override fun getCount(): Int = fragmentList.size
}