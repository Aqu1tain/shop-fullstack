export const useAuth = () => {
  const user = useState<{ username: string; role: string } | null>('user', () => null)
  const token = useState<string | null>('token', () => null)

  const isAuthenticated = computed(() => !!token.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')

  const config = useRuntimeConfig()

  async function login(username: string, password: string) {
    const data = await $fetch<{ token: string; username: string; role: string }>(`${config.public.apiBase}/auth/login`, {
      method: 'POST',
      body: { username, password }
    })
    setAuth(data)
    return data
  }

  async function register(username: string, email: string, password: string) {
    const data = await $fetch<{ token: string; username: string; role: string }>(`${config.public.apiBase}/auth/register`, {
      method: 'POST',
      body: { username, email, password }
    })
    setAuth(data)
    return data
  }

  function setAuth(data: { token: string; username: string; role: string }) {
    token.value = data.token
    user.value = { username: data.username, role: data.role }
    if (import.meta.client) {
      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify(user.value))
    }
  }

  function logout() {
    token.value = null
    user.value = null
    if (import.meta.client) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  }

  function init() {
    if (import.meta.client) {
      const savedToken = localStorage.getItem('token')
      const savedUser = localStorage.getItem('user')
      if (savedToken && savedUser) {
        token.value = savedToken
        user.value = JSON.parse(savedUser)
      }
    }
  }

  return { user, token, isAuthenticated, isAdmin, login, register, logout, init }
}
