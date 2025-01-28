resource "aws_iam_openid_connect_provider" "eks_oidc_provider" {
  client_id_list = var.client_id_list
  url = var.url
  thumbprint_list = ["55635cfea6a15f4770cc5ec0977492b318f9b0cc"]  # AWS>의 OIDC thumbprint
}