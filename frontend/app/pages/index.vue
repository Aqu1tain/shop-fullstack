<script setup lang="ts">
const config = useRuntimeConfig()

const search = ref('')
const selectedCategory = ref('all')
const page = ref(0)

const { data: categories } = await useFetch<any[]>(`${config.public.apiBase}/products/categories`)

const { data: productsData, refresh } = await useFetch<any>(() => {
  let url = `${config.public.apiBase}/products?page=${page.value}&size=12`
  if (search.value) {
    url = `${config.public.apiBase}/products/search?query=${search.value}&page=${page.value}&size=12`
  } else if (selectedCategory.value && selectedCategory.value !== 'all') {
    url = `${config.public.apiBase}/products/category/${selectedCategory.value}?page=${page.value}&size=12`
  }
  return url
})

const products = computed(() => productsData.value?.content || [])
const totalPages = computed(() => productsData.value?.totalPages || 0)

function handleSearch() {
  page.value = 0
  selectedCategory.value = 'all'
  refresh()
}

function handleCategoryChange() {
  page.value = 0
  search.value = ''
  refresh()
}
</script>

<template>
  <UContainer class="py-8">
    <h1 class="text-3xl font-bold mb-6">Products</h1>

    <div class="flex gap-4 mb-6">
      <UInput v-model="search" placeholder="Search products..." class="flex-1" @keyup.enter="handleSearch" />
      <UButton @click="handleSearch">Search</UButton>
      <USelect v-model="selectedCategory" :items="[{ label: 'All Categories', value: 'all' }, ...(categories || []).map((c: any) => ({ label: c.name, value: String(c.id) }))]" @update:model-value="handleCategoryChange" />
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <UCard v-for="product in products" :key="product.id" class="cursor-pointer hover:shadow-lg transition-shadow" @click="navigateTo(`/products/${product.id}`)">
        <img :src="product.imageUrl" :alt="product.name" class="w-full h-40 object-cover rounded mb-4" />
        <h3 class="font-semibold">{{ product.name }}</h3>
        <p class="text-sm text-gray-500">{{ product.categoryName }}</p>
        <p class="text-lg font-bold text-primary mt-2">{{ product.price }} EUR</p>
      </UCard>
    </div>

    <div v-if="totalPages > 1" class="flex justify-center gap-4 mt-8">
      <UButton variant="outline" :disabled="page === 0" @click="page--; refresh()">Previous</UButton>
      <span class="py-2">Page {{ page + 1 }} / {{ totalPages }}</span>
      <UButton variant="outline" :disabled="page >= totalPages - 1" @click="page++; refresh()">Next</UButton>
    </div>
  </UContainer>
</template>
