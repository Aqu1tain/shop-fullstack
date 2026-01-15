<script setup lang="ts">
const { isAuthenticated, isAdmin, logout } = useAuth()

function handleLogout() {
  logout()
  navigateTo('/login')
}
</script>

<template>
  <UApp>
    <UHeader>
      <template #left>
        <NuxtLink to="/" class="text-xl font-bold">Shop</NuxtLink>
      </template>

      <template #right>
        <UButton to="/" variant="ghost">Products</UButton>
        <template v-if="isAuthenticated">
          <UButton to="/orders" variant="ghost">My Orders</UButton>
          <UButton to="/profile" variant="ghost">Profile</UButton>
          <UButton v-if="isAdmin" to="/admin" variant="ghost" color="primary">Admin</UButton>
          <UButton @click="handleLogout" variant="outline">Logout</UButton>
        </template>
        <template v-else>
          <UButton to="/login" variant="ghost">Login</UButton>
          <UButton to="/register" variant="outline">Register</UButton>
        </template>
        <UColorModeButton />
      </template>
    </UHeader>

    <UMain>
      <NuxtPage />
    </UMain>

    <UFooter>
      <template #left>
        <p class="text-sm text-gray-500">Shop - Built with Nuxt UI</p>
      </template>
    </UFooter>
  </UApp>
</template>
