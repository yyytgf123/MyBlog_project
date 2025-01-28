resource "kubernetes_service_account" "example"{
  metadata{
    labels = var.sa-labels
    name = var.sa-name
    namespace = var.sa-namespace
    annotations = var.sa-annotations
  }
}