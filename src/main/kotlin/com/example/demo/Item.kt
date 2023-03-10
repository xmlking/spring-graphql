package com.example.demo

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.graphql.data.GraphQlRepository
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Entity
data class Item(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val description: String?
)

data class ItemInput(
    val name: String,
    val description: String?
) {
    fun toEntity() = Item(name = this.name, description = this.description)
}

@GraphQlRepository
interface ItemRepository : CrudRepository<Item, Long>, QuerydslPredicateExecutor<Item>

@Controller
class ItemController(private val itemRepository: ItemRepository) {

    @MutationMapping
    fun addItem(@Argument item: ItemInput): Item {
        return itemRepository.save(item.toEntity())
    }

    @MutationMapping
    fun deleteItem(@Argument id: Long): Item? {
        return itemRepository.findById(id)
            .orElse(null)
            ?.also { itemRepository.deleteById(id) }
    }
}
