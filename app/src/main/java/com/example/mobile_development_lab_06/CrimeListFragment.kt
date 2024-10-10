package com.example.mobile_development_lab_06

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_development_lab_06.databinding.FragmentCrimeListBinding
import com.example.mobile_development_lab_06.databinding.ListItemCrimeBinding
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }
    private var callbacks: Callbacks? = null

    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private var _binding: FragmentCrimeListBinding? = null // Объявляем переменную для binding
    private val binding get() = _binding!! // Создаем геттер для безопасного доступа

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java] // Используем ViewModelProvider
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.fragment_crime_list, menu)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)

        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.crimeRecyclerView.adapter = adapter


        return binding.root // Возвращаем корневой элемент из binding
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        binding.crimeRecyclerView.adapter = adapter
    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        crimeListViewModel.crimeListLiveData.observe(
//            viewLifecycleOwner,
//            Observer { crimes ->
//                crimes?.let {
//                    Log.i(TAG, "Got crimes${crimes.size}")
//                    updateUI(crimes)
//                }
//            })
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Получаем menuHost из активности
//        val menuHost: MenuHost = requireActivity()
//
//        // Добавляем MenuProvider
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.fragment_crime_list, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return when (menuItem.itemId) {
////                    R.id.some_menu_item -> {
////                        // Код для обработки нажатия на элемент меню
////                        true
////                    }
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner)
//
//        // Наблюдаем за изменениями в crimeListLiveData
//        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner) { crimes ->
//            crimes?.let {
//                Log.i(TAG, "Got crimes: ${crimes.size}")
//                updateUI(crimes)
//            }
//        }
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем menuHost из активности
        val menuHost: MenuHost = requireActivity()

        // Добавляем MenuProvider
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_crime_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.new_crime -> {
                        Log.d("CrimeListFragment", "New crime clicked!")
                        val crime: Crime = Crime()
                        crimeListViewModel.addCrime(crime)
                        callbacks?.onCrimeSelected(crime.id)
                        true
                    }
//                    else -> return super.onOptionsItemSelected(item)
                    else -> false

                }
            }
        }, viewLifecycleOwner)

        // Наблюдаем за изменениями в crimeListLiveData
        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner) { crimes ->
            crimes?.let {
                Log.i(TAG, "Got crimes: ${crimes.size}")
                updateUI(crimes)
            }
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val menuHost: MenuHost = requireActivity()
//
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.fragment_crime_list, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return when (menuItem.itemId) {
//                    R.id.new_crime -> {
//                        showNewCrime()
//                        true
//                    }
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner)
//    }




    private inner class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var crime: Crime

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            binding.crimeTitle.text = this.crime.title

            // Форматируем дату
            val formattedDate = formatDate(crime.date)
            binding.crimeDate.text = formattedDate

            binding.crimeSolved.visibility =
                if (crime.isSolved) View.VISIBLE
                else View.GONE
        }

        private fun formatDate(date: Date): String {
            val dayOfWeek = DateFormat.format("EEEE", date).toString() // Получаем день недели
            val monthDayYear = DateFormat.format("MMM dd, yyyy", date).toString() // Форматируем оставшуюся часть даты
            return "$dayOfWeek, $monthDayYear" // Объединяем строки
        }

        override fun onClick(v: View) {
            Log.d("CrimeListFragment", "${crime.title} clicked!")
            callbacks?.onCrimeSelected(crime.id)
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val binding = ListItemCrimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CrimeHolder(binding)
        }
        override fun getItemCount() =
            crimes.size
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }
    }
    // Форматируем дату в виде "Monday, Jul 22, 2019"

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
//    }
    // Инициализация binding

    // Установка LayoutManager для RecyclerView

    // Установка адаптера для RecyclerView
//        binding.crimeRecyclerView.adapter = CrimeAdapter(crimeListViewModel.crimes)

//        updateUI()
//            Toast.makeText(context, "${this.crime.title} pressed!", Toast.LENGTH_SHORT).show()

//    private fun updateUI() {
//        val crimes = crimeListViewModel.crimes
//        adapter = CrimeAdapter(crimes)
//        binding.crimeRecyclerView.adapter = adapter
//    }

//    private inner class CrimeAdapter(private val crimes: List<Crime>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


//        private val VIEW_TYPE_NORMAL = 0
//        private val VIEW_TYPE_SERIOUS = 1
//
//        override fun getItemViewType(position: Int): Int {
//            return if (crimes[position].requiresPolice) {
//                VIEW_TYPE_SERIOUS // Возвращаем тип для серьезных преступлений
//            } else {
//                VIEW_TYPE_NORMAL // Возвращаем тип для обычных преступлений
//            }
//        }

//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
//            val binding = ListItemCrimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return CrimeHolder(binding)
//        }
//
//        override fun getItemCount() = crimes.size
//
//        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
//            val crime = crimes[position]
//            holder.bind(crime)
//        }

//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            val crime = crimes[position]
//            holder.bind
//            when (holder) {
//                is CrimeHolder -> holder.bind(crime)
////                is SeriousCrimeHolder -> holder.bind(crime)
//            }
//        }
//    }

//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//            return when (viewType) {
//                VIEW_TYPE_SERIOUS -> {
//                    val binding = ListItemSeriousCrimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                    SeriousCrimeHolder(binding)
//                }
//                else -> {
//                    val binding = ListItemCrimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                    CrimeHolder(binding)
//                }
//            }
//        }
//
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            val crime = crimes[position]
//            when (holder) {
//                is CrimeHolder -> holder.bind(crime)
//                is SeriousCrimeHolder -> holder.bind(crime)
//            }
//        }


//    }

//    private inner class SeriousCrimeHolder(private val binding: ListItemSeriousCrimeBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(crime: Crime) {
//            binding.crimeTitle.text = crime.title
//            binding.crimeDate.text = crime.date.toString()
//
//            // Установите обработчик нажатия для кнопки "Связаться с полицией"
//            binding.contactPoliceButton.setOnClickListener {
//                Toast.makeText(context, "Contacting police for ${crime.title}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождаем память при уничтожении представления
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}
