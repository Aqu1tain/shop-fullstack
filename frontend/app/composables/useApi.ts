export const useApi = () => {
  const config = useRuntimeConfig()
  const { token } = useAuth()

  async function api<T>(url: string, options: any = {}) {
    const headers: Record<string, string> = {}
    if (token.value) {
      headers.Authorization = `Bearer ${token.value}`
    }
    return $fetch<T>(`${config.public.apiBase}${url}`, {
      ...options,
      headers: { ...headers, ...options.headers }
    })
  }

  return { api }
}
