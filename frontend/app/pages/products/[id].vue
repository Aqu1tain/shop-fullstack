<script setup lang="ts">
const route = useRoute()
const { api } = useApi()
const { isAuthenticated } = useAuth()
const config = useRuntimeConfig()

const quantity = ref(1)
const ordering = ref(false)
const message = ref('')
const messageType = ref<'success' | 'error'>('success')

const { data: product } = await useFetch<any>(`${config.public.apiBase}/products/${route.params.id}`)

async function placeOrder() {
  if (!isAuthenticated.value) {
    navigateTo('/login')
    return
  }

  ordering.value = true
  message.value = ''
  try {
    await api('/orders', {
      method: 'POST',
      body: { productId: product.value.id, quantity: quantity.value }
    })
    message.value = 'Order placed successfully!'
    messageType.value = 'success'
    product.value.stockQuantity -= quantity.value
  } catch (e: any) {
    message.value = e.data?.error || 'Failed to place order'
    messageType.value = 'error'
  } finally {
    ordering.value = false
  }
}
</script>

<template>
  <UContainer class="py-8">
    <UCard v-if="product" class="max-w-4xl mx-auto">
      <div class="grid md:grid-cols-2 gap-8">
        <img :src="product.imageUrl" :alt="product.name" class="w-full rounded-lg" />
        <div>
          <h1 class="text-3xl font-bold mb-2">{{ product.name }}</h1>
          <UBadge color="neutral" class="mb-4">{{ product.categoryName }}</UBadge>
          <p class="text-3xl font-bold text-primary mb-4">{{ product.price }} EUR</p>
          <p class="text-gray-600 mb-4">{{ product.description }}</p>
          <p class="mb-6">Stock: {{ product.stockQuantity }}</p>

          <div v-if="product.stockQuantity > 0" class="flex gap-4 items-center">
            <UInput v-model.number="quantity" type="number" min="1" :max="product.stockQuantity" class="w-24" />
            <UButton @click="placeOrder" :loading="ordering">Order Now</UButton>
          </div>
          <UAlert v-else color="error" title="Out of stock" />

          <UAlert v-if="message" :color="messageType === 'success' ? 'success' : 'error'" :title="message" class="mt-4" />
        </div>
      </div>
    </UCard>
  </UContainer>
</template>
